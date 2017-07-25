package es.mnmapp.aolv.meneame.ui.view.main.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

@Module abstract class MainFragmentProvider {

    @ContributesAndroidInjector(modules = arrayOf(MainFragmentModule::class))
    abstract fun provideMainFragmentFactory() : MainFragment
}
