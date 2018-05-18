package es.mnmapp.aolv.meneame

import com.facebook.stetho.Stetho

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Suppress("unused")
class MnmDebugApp : MnmApp() {

    // Application overrides -----
    override fun onCreate() {
        super.onCreate()

        initStetho()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }
}
