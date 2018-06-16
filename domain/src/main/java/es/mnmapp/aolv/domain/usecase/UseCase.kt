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

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable

/**
 * Base class for use case implementations
 */
abstract class UseCase<T, in Params>(
    private val postExecutionThread: Scheduler,
    private val workerThread: Scheduler
) {

    // Fields -----

    private val disposables: CompositeDisposable = CompositeDisposable()

    // Overridable methods -----

    /**
     * Should be used when the use case type is of type flowable
     *
     * @see Type.Flowable
     *
     * @param[params] Use case parameters
     * @return flowable
     */
    internal open fun buildFlowableUseCase(params: Params): Flowable<T> {
        throw RuntimeException("Flowable use case is not implemented for ${this.javaClass.simpleName}")
    }

    /**
     * Should be used when the use case type is of type single
     *
     * @see Type.Single
     *
     * @param[params] Use case parameters
     * @return single
     */
    internal open fun buildSingleUseCase(params: Params): Single<T> {
        throw RuntimeException("Single use case is not implemented for ${this.javaClass.simpleName}")
    }

    // Class member abstract methods -----

    /**
     * Determines which build method has the use case implementation override
     *
     * @see UseCase.buildFlowableUseCase
     * @see UseCase.buildSingleUseCase
     *
     * @return type of use case
     */
    protected abstract fun getUseCaseType(): Type

    // Class member methods -----

    /**
     * Force flowable to be observe and subscribe on worker threads
     *
     * @return flowable transformer
     */
    protected fun <T> applyFlowableWorkerSchedulers(): FlowableTransformer<T, T> =
        FlowableTransformer {
            it.subscribeOn(workerThread)
                .observeOn(workerThread)
        }

    /**
     * Force single to be observe and subscribe on worker threads
     *
     * @return single transformer
     */
    protected fun <T> applySingleWorkerSchedulers(): SingleTransformer<T, T> =
        SingleTransformer {
            it.subscribeOn(workerThread)
                .observeOn(workerThread)
        }

    // Class methods -----

    /**
     * Executes use case and notifies result through passed actions
     *
     * @param[params] Use case execution parameters
     * @param[successAction] Action to be invoked in case of successful result
     * @param[errorAction] Action to be invoked in case of error
     */
    fun execute(
        params: Params,
        successAction: ((T) -> Unit) = {},
        errorAction: ((Throwable) -> Unit) = {}
    ) {
        when (getUseCaseType()) {
            Type.Flowable -> {
                this.buildFlowableUseCase(params)
                    .compose(applyFlowableSchedulers())
                    .subscribe(successAction, errorAction)
                    .apply { disposables.add(this) }
            }
            Type.Single -> {
                this.buildSingleUseCase(params)
                    .compose(applySingleSchedulers())
                    .subscribe(successAction, errorAction)
                    .apply { disposables.add(this) }
            }
        }
    }

    private fun <T> applyFlowableSchedulers(): FlowableTransformer<T, T> =
        FlowableTransformer {
            it.subscribeOn(workerThread)
                .observeOn(postExecutionThread)
        }

    private fun <T> applySingleSchedulers(): SingleTransformer<T, T> =
        SingleTransformer {
            it.subscribeOn(workerThread)
                .observeOn(postExecutionThread)
        }

    /**
     * Clears pending use case work
     */
    fun clear() {
        disposables.clear()
    }

    // Protected classes -----

    protected enum class Type { Flowable, Single }
}
