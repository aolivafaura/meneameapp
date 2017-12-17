package es.mnmapp.aolv.meneame.utils

import es.mnmapp.aolv.meneame.loggers.AnalitycsLogger
import es.mnmapp.aolv.meneame.ui.BaseActivity
import timber.log.Timber

/**
 * Created by antoniojoseoliva on 04/08/2017.
 */

class Lg(val analitycsLogger: AnalitycsLogger) {

    fun d(message: String?) {
        Timber.d(message)
    }

    fun w(message: String?) {
        Timber.w(message)
    }

    fun i(message: String?) {
        Timber.i(message)
    }

    fun v(message: String?) {
        Timber.v(message)
    }

    fun e(message: String?) {
        Timber.e(message)
    }

    fun sc(activity: BaseActivity, screenName: String) {
        analitycsLogger.setCurrentScreen(activity, screenName)
    }

    fun ev(name: String, params: Map<String, String>?) {
        analitycsLogger.logEvent(name, params)
    }
}