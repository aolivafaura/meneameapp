/*
 *     Copyright 2018 @ https://github.com/aolivafaura/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.shouldNotThrowException
import es.mnmapp.aolv.domain.shouldThrowException
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.CountDownLatch

/**
 * Test class for UseCase
 *
 * @see UseCase
 */
class UseCaseTest {

    // Fields -----

    private val scheduler = TestScheduler()
    private val controlValue = ""
    private val countDownLatch = CountDownLatch(1)

    // Test cases -----

    @Test
    fun `Given single use case implementation, when single use case is invoked, then use case executes correctly`() {
        val singleImplementation = SingleUseCaseRightImplementation(scheduler, scheduler)
        shouldNotThrowException { singleImplementation.execute(controlValue) }
    }

    @Test
    fun `Given single use case implementation, when flowable use case is invoked, then use case should fail`() {
        val singleImplementation = SingleUseCaseWrongImplementation(scheduler, scheduler)
        shouldThrowException { singleImplementation.execute(controlValue) }
    }

    @Test
    fun `Given flowable use case implementation, when flowable use case is invoked, then use case executes correctly`() {
        val flowableImplementation = FlowableUseCaseRightImplementation(scheduler, scheduler)
        shouldNotThrowException { flowableImplementation.execute(controlValue) }
    }

    @Test
    fun `Given flowable use case implementation, when single use case is invoked, then use case should fail`() {
        val flowableImplementation = FlowableUseCaseWrongImplementation(scheduler, scheduler)
        shouldThrowException { flowableImplementation.execute(controlValue) }
    }

    @Test
    fun `Given flowable use case, when use case is built, then result is dispatched without modifications`() {
        // Given
        val flowableImplementation = FlowableUseCaseRightImplementation(scheduler, scheduler)
        // When
        var returnedValue: String? = null
        flowableImplementation.execute(
            controlValue,
            {
                returnedValue = it
                countDownLatch.countDown()
            },
            {
            }
        )
        // Then
        scheduler.triggerActions()
        countDownLatch.await()
        assertEquals(controlValue, returnedValue)
    }

    @Test
    fun `Given single use case, when use case is built, then result is dispatched without modifications`() {
        // Given
        val singleImplementation = SingleUseCaseRightImplementation(scheduler, scheduler)
        // When
        var returnedValue: String? = null
        singleImplementation.execute(
            controlValue,
            {
                returnedValue = it
                countDownLatch.countDown()
            },
            {
            }
        )
        // Then
        scheduler.triggerActions()
        countDownLatch.await()
        assertEquals(controlValue, returnedValue)
    }

    // Internal classes -----

    internal class SingleUseCaseRightImplementation(
        postExecutionThread: Scheduler,
        workerThread: Scheduler
    ) : UseCase<String, String>(postExecutionThread, workerThread) {

        override fun getUseCaseType(): Type = Type.Single

        override fun buildSingleUseCase(params: String): Single<String> = Single.just(params)
    }

    internal class SingleUseCaseWrongImplementation(
        postExecutionThread: Scheduler,
        workerThread: Scheduler
    ) : UseCase<String, String>(postExecutionThread, workerThread) {

        override fun getUseCaseType(): Type = Type.Single

        override fun buildFlowableUseCase(params: String): Flowable<String> = Flowable.just(params)
    }

    internal class FlowableUseCaseRightImplementation(
        postExecutionThread: Scheduler,
        workerThread: Scheduler
    ) : UseCase<String, String>(postExecutionThread, workerThread) {

        override fun getUseCaseType(): Type = Type.Flowable

        override fun buildFlowableUseCase(params: String): Flowable<String> = Flowable.just(params)
    }

    internal class FlowableUseCaseWrongImplementation(
        postExecutionThread: Scheduler,
        workerThread: Scheduler
    ) : UseCase<String, String>(postExecutionThread, workerThread) {

        override fun getUseCaseType(): Type = Type.Flowable

        override fun buildSingleUseCase(params: String): Single<String> = Single.just(params)
    }
}