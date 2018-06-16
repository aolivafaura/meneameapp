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

class UseCaseTest {

    private val scheduler = TestScheduler()

    private val controlValue = ""

    private val countDownLatch = CountDownLatch(1)

    @Test
    fun `Given single use case implementation, when single use case is invoked, then use case executes correctly`() {
        val singleImplementation = SingleUseCaseRightImplementation(scheduler, scheduler);

        { singleImplementation.execute(controlValue) }.shouldNotThrowException()
    }

    @Test
    fun `Given single use case implementation, when flowable use case is invoked, then use case should fail`() {
        val singleImplementation = SingleUseCaseWrongImplementation(scheduler, scheduler);

        { singleImplementation.execute(controlValue) }.shouldThrowException()
    }

    @Test
    fun `Given flowable use case implementation, when flowable use case is invoked, then use case executes correctly`() {
        val flowableImplementation = FlowableUseCaseRightImplementation(scheduler, scheduler);

        { flowableImplementation.execute(controlValue) }.shouldNotThrowException()
    }

    @Test
    fun `Given flowable use case implementation, when single use case is invoked, then use case should fail`() {
        val flowableImplementation = FlowableUseCaseWrongImplementation(scheduler, scheduler);

        { flowableImplementation.execute(controlValue) }.shouldThrowException()
    }

    @Test
    fun `Given flowable use case, when use case is built, then result is dispatched without modifications`() {
        val flowableImplementation = FlowableUseCaseRightImplementation(scheduler, scheduler);

        var returnedValue: String? = null
        flowableImplementation.execute(
                controlValue,
                {
                    returnedValue = it
                    countDownLatch.countDown()
                }
        )

        scheduler.triggerActions()
        countDownLatch.await()
        assertEquals(controlValue, returnedValue)
    }

    @Test
    fun `Given single use case, when use case is built, then result is dispatched without modifications`() {
        val singleImplementation = SingleUseCaseRightImplementation(scheduler, scheduler);

        var returnedValue: String? = null
        singleImplementation.execute(
                controlValue,
                {
                    returnedValue = it
                    countDownLatch.countDown()
                }
        )

        scheduler.triggerActions()
        countDownLatch.await()
        assertEquals(controlValue, returnedValue)
    }

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