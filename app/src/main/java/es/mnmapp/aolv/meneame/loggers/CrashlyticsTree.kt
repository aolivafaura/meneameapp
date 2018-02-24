package es.mnmapp.aolv.meneame.loggers

import android.util.Log
import com.crashlytics.android.Crashlytics
import timber.log.Timber

/**
 * Created by antoniojoseoliva on 26/07/2017.
 */

class CrashlyticsTree : Timber.Tree() {

    override fun log(priority: Int, tag: String, message: String, t: Throwable?) {

        when (priority) {
            Log.VERBOSE, Log.DEBUG, Log.INFO -> return
        }

        Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
        Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
        Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)

        t?.let { Crashlytics.logException(t) } ?: Crashlytics.logException(Exception(message))
    }

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"
    }
}