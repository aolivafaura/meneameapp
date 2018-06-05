package es.mnmapp.aolv.meneame

import android.annotation.SuppressLint
import android.app.Application
import com.antoniooliva.logger.Lgr
import es.mnmapp.aolv.meneame.di.getKoinModules
import org.koin.android.ext.android.startKoin

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@SuppressLint("Registered")
open class MnmApp : Application() {

    // Application overrides -----
    override fun onCreate() {
        super.onCreate()

        initKoin()
        initLogger()
    }

    private fun initKoin() {
        startKoin(this, getKoinModules())
    }

    private fun initLogger() {
        Lgr.initialize(this)
    }
}
