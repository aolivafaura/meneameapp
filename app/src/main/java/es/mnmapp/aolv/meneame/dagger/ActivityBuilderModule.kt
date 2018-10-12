package es.mnmapp.aolv.meneame.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.mnmapp.aolv.meneame.ui.view.MainActivity

@Module
abstract class ActivityBuilderModule {

//    @PerActivity
    @ContributesAndroidInjector(modules = [(FragmentBuilderModule::class)])
    internal abstract fun bindMainActivity(): MainActivity
}