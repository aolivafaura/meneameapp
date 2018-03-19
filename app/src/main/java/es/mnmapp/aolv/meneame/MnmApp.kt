package es.mnmapp.aolv.meneame

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import es.mnmapp.aolv.meneame.koin.getKoinModules
import es.mnmapp.aolv.meneame.loggers.CrashlyticsTree
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

open class MnmApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initFabric()
        initTimber()
    }

    private fun initKoin() {
        startKoin(this, getKoinModules())
    }

    private fun initTimber() {
        Timber.plant(if (BuildConfig.DEBUG) DebugTree() else CrashlyticsTree())
    }

    private fun initFabric() {
        val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build())
    }
}
