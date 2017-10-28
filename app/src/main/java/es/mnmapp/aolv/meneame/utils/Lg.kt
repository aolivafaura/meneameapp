package es.mnmapp.aolv.meneame.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import es.mnmapp.aolv.meneame.ui.BaseActivity
import timber.log.Timber



/**
 * Created by antoniojoseoliva on 04/08/2017.
 */

class Lg(val firebaseAnalytics: FirebaseAnalytics) {

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
        firebaseAnalytics.setCurrentScreen(activity, screenName, null)
    }

    fun ev(name: String, params: Map<String, String>?) {
        val bundle = Bundle()
        params?.let{
            for (entry in it.entries) {
                bundle.putString(entry.key, entry.value)
            }
        }

        firebaseAnalytics.logEvent(name, bundle)
    }
}