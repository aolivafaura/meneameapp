package es.mnmapp.aolv.meneame.rx

import android.support.annotation.CallSuper
import io.reactivex.observers.DisposableObserver
import timber.log.Timber

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

open class BaseObserver<T> : DisposableObserver<T>() {

    @CallSuper
    override fun onError(error : Throwable) {
        Timber.w(error.message ?: "Null exception on base observer")
    }

    override fun onNext(result : T) {

    }

    override fun onComplete() {

    }

}
