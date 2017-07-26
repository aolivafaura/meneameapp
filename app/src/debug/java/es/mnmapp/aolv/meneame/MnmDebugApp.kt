package es.mnmapp.aolv.meneame

import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

class MnmDebugApp : MnmApp() {

    override fun onCreate() {
        super.onCreate()

        Timber.d("APPLICATION ON DEBUG MODE")

        initStetho()
        initLeakCanary()
    }

    fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    fun initLeakCanary() {
        LeakCanary.isInAnalyzerProcess(this).let { LeakCanary.install(this) }
    }
}
