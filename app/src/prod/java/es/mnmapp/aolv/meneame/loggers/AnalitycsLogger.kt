package es.mnmapp.aolv.meneame.loggers

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import es.mnmapp.aolv.meneame.ui.BaseActivity

/**
 * Created by antonio on 11/1/17.
 */

class AnalitycsLogger(val context: Context){

    fun logEvent(name: String, params: Map<String, String>?) {
        val bundle = Bundle()
        params?.let{
            for (entry in it.entries) {
                bundle.putString(entry.key, entry.value)
            }
        }

        FirebaseAnalytics.getInstance(context).logEvent(name, bundle)
    }

    fun setCurrentScreen(activity: BaseActivity, screen: String) {
        FirebaseAnalytics.getInstance(context).setCurrentScreen(activity, screen, null)
    }
}