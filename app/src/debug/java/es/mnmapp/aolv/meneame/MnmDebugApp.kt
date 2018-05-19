package es.mnmapp.aolv.meneame

import android.os.Build
import com.facebook.stetho.Stetho



/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Suppress("unused")
class MnmDebugApp : MnmApp() {

    // Application overrides -----
    override fun onCreate() {
        super.onCreate()

        if (isNotUnitTestingRun()) {
            initStetho()
        }
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun isNotUnitTestingRun() = "robolectric" != Build.FINGERPRINT
}
