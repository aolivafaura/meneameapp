package es.mnmapp.aolv.meneame.loggers

import android.content.Context
import android.os.Bundle
import es.mnmapp.aolv.meneame.ui.BaseActivity
import timber.log.Timber

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

        Timber.i("EVENT LOGGED: ", name, params)
    }

    fun setCurrentScreen(activity: BaseActivity, screen: String) {
        Timber.i("SCREEN SET: ", activity.toString(), screen)
    }
}