package es.mnmapp.aolv.meneame

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import es.mnmapp.aolv.meneame.di.DaggerAppComponent
import ir.mirrajabi.kotlinpreferencesextensions.KotlinPreferences
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

open class MnmApp : Application(), HasActivityInjector {

    @Inject lateinit var activityDispatchingAndroidInjector : DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initDagger()
        initPreferences()
    }

    fun initTimber() {

        BuildConfig.DEBUG.let { Timber.plant(DebugTree()) }
    }

    fun initDagger() {

        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    fun initPreferences() {

        KotlinPreferences.init(applicationContext)
    }

    override fun activityInjector() : AndroidInjector<Activity> = activityDispatchingAndroidInjector
}
