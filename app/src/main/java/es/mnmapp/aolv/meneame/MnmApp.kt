package es.mnmapp.aolv.meneame

import android.annotation.SuppressLint
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import es.mnmapp.aolv.meneame.di.getKoinModules
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@SuppressLint("Registered")
open class MnmApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initFabric()
    }

    private fun initKoin() {
        startKoin(this, getKoinModules())
    }

    private fun initFabric() {
        val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build())
    }
}
