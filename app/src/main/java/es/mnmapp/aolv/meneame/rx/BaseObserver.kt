package es.mnmapp.aolv.meneame.rx

import android.support.annotation.CallSuper
import es.mnmapp.aolv.meneame.utils.w
import io.reactivex.observers.DisposableObserver

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

open class BaseObserver<T> : DisposableObserver<T>() {

    @CallSuper
    override fun onError(error : Throwable) {
        w(error.message ?: "Null exception on base observer")
    }

    override fun onNext(result : T) {

    }

    override fun onComplete() {

    }
}
