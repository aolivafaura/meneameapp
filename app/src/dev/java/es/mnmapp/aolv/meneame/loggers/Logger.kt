package es.mnmapp.aolv.meneame.loggers

import timber.log.Timber

/**
 * Created by antoniojoseoliva on 26/07/2017.
 */

class Logger {

    init {
        Timber.plant(Timber.DebugTree())
    }

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
}