package es.mnmapp.aolv.meneame.loggers

import android.content.Context
import android.os.Bundle
import android.util.Log
import es.mnmapp.aolv.meneame.ui.BaseActivity

/**
 * Created by antonio on 11/1/17.
 */

class AnalitycsLogger(val context: Context) {

    fun logEvent(name: String, params: Map<String, String>?) {
        val bundle = Bundle()
        params?.let {
            for (entry in it.entries) {
                bundle.putString(entry.key, entry.value)
            }
        }

        Log.i("AnalyticsLogger", "EVENT LOGGED: $name, $params")
    }

    fun setCurrentScreen(activity: BaseActivity, screen: String) {
        Log.i("AnalyticsLogger", "SCREEN SET: $screen")
    }
}