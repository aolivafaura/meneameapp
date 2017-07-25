package es.mnmapp.aolv.domain.usecase

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by antoniojoseoliva on 08/07/2017.
 */
abstract class UseCase<T, in Params> {

    @field:[Inject Named("uiThread")] lateinit var postExecutionThread : Scheduler

    @field:[Inject Named("workerThread")] lateinit var workerThread : Scheduler

    private val disposables : CompositeDisposable = CompositeDisposable()

    internal abstract fun buildUseCaseObservable(params : Params) : Observable<T>

    fun execute(observer : DisposableObserver<T>, params : Params) {
        val observable = this.buildUseCaseObservable(params).subscribeOn(workerThread).observeOn(
                postExecutionThread)

        addDisposable(observable.subscribeWith(observer))
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable : Disposable) {
        disposables.add(disposable)
    }
}
