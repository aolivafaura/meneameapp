package es.mnmapp.aolv.domain.usecase

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
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

    fun execute(params: Params,
                onNext: ((T) -> Unit)? = {},
                onError: ((Throwable) -> Unit)? = {}) {

        when (getUseCaseType()) {
            Type.Flowable -> {
                this.buildFlowableUseCase(params)
                    .subscribeOn(workerThread)
                    .observeOn(postExecutionThread)
                    .subscribe(onNext, onError)
                    .apply { disposables.add(this) }
            }
            Type.Single -> {
                this.buildSingleUseCase(params)
                    .subscribeOn(workerThread)
                    .observeOn(postExecutionThread)
                    .subscribe(onNext, onError)
                    .apply { disposables.add(this) }
            }
        }
    }

    fun clear() {
        disposables.clear()
    }

    internal enum class Type { Flowable, Single }
}
