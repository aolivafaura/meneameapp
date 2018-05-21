package es.mnmapp.aolv.domain.usecase

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by antoniojoseoliva on 08/07/2017.
 */
abstract class UseCase<T, in Params>(private val postExecutionThread: Scheduler,
                                     private val workerThread: Scheduler) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    internal abstract fun buildUseCaseObservable(params: Params): Flowable<T>

    fun execute(params: Params,
                onNext: ((T) -> Unit)? = {},
                onError: ((Throwable) -> Unit)? = {},
                onComplete: (() -> Unit)? = {}) {

        this.buildUseCaseObservable(params)
                .subscribeOn(workerThread)
                .observeOn(postExecutionThread)
                .subscribe(onNext, onError, onComplete)
                .apply { disposables.add(this) }
    }

    fun clear() {
        disposables.clear()
    }
}
