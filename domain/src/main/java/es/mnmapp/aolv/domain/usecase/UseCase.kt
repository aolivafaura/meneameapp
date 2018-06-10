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

abstract class UseCase<T, in Params>(
    private val postExecutionThread: Scheduler,
    private val workerThread: Scheduler
) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    internal open fun buildFlowableUseCase(params: Params): Flowable<T> {
        throw RuntimeException("Flowable use case is not implemented for ${this.javaClass.simpleName}")
    }

    internal open fun buildSingleUseCase(params: Params): Single<T> {
        throw RuntimeException("Single use case is not implemented for ${this.javaClass.simpleName}")
    }

    internal abstract fun getUseCaseType(): Type

    fun execute(params: Params, onNext: ((T) -> Unit)? = {}, onError: ((Throwable) -> Unit)? = {}) {
        when (getUseCaseType()) {
            Type.Flowable -> {
                this.buildFlowableUseCase(params)
                    .compose(applyFlowableSchedulers())
                    .subscribe(onNext, onError)
                    .apply { disposables.add(this) }
            }
            Type.Single -> {
                this.buildSingleUseCase(params)
                    .compose(applySingleSchedulers())
                    .subscribe(onNext, onError)
                    .apply { disposables.add(this) }
            }
        }
    }

    fun clear() {
        disposables.clear()
    }

    protected fun <T> applyFlowableWorkerSchedulers(): FlowableTransformer<T, T> =
        FlowableTransformer {
            it.subscribeOn(workerThread)
                .observeOn(workerThread)
        }

    protected fun <T> applySingleWorkerSchedulers(): SingleTransformer<T, T> =
        SingleTransformer {
            it.subscribeOn(workerThread)
                .observeOn(workerThread)
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

    internal enum class Type { Flowable, Single }
}
