package es.mnmapp.aolv.meneame

import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import es.mnmapp.aolv.meneame.di.DaggerAppComponent
import es.mnmapp.aolv.meneame.loggers.CrashlyticsTree
import io.fabric.sdk.android.Fabric
import ir.mirrajabi.kotlinpreferencesextensions.KotlinPreferences
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

open class MnmApp : Application(), HasActivityInjector {

    @Inject lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        initFabric()
        initTimber()
        initDagger()
        initPreferences()
    }

    private fun initTimber() {
        Timber.plant(if (BuildConfig.DEBUG) DebugTree() else CrashlyticsTree())
    }

    private fun initDagger() {
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    private fun initPreferences() {
        KotlinPreferences.init(applicationContext)
    }

    private fun initFabric() {
        val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build())
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector
}
