package es.mnmapp.aolv.meneame

import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import es.mnmapp.aolv.meneame.utils.d

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

class MnmDebugApp : MnmApp() {

    override fun onCreate() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        super.onCreate()

        d("APPLICATION ON DEBUG MODE")
        initLeakCanary()
        initStetho()
    }

    fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    fun initLeakCanary() {
        LeakCanary.install(this)
    }
}
