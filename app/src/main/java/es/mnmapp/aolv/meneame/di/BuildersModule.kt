package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.mnmapp.aolv.meneame.di.scopes.PerActivity
import es.mnmapp.aolv.meneame.ui.view.main.MainActivity
import es.mnmapp.aolv.meneame.ui.view.main.MainActivityModule
import es.mnmapp.aolv.meneame.ui.view.main.fragment.MainFragmentProvider

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Module abstract class BuildersModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, MainFragmentProvider::class))
    abstract fun bindMainActivity() : MainActivity
}
