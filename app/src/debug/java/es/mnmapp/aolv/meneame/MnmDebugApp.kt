package es.mnmapp.aolv.meneame

import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

class MnmDebugApp : MnmApp() {

    override fun onCreate() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        super.onCreate()

        initLeakCanary()
        initStetho()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initLeakCanary() {
        LeakCanary.install(this)
    }
}
