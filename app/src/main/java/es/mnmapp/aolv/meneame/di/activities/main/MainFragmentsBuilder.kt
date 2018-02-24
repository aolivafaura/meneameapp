package es.mnmapp.aolv.meneame.di.activities.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.mnmapp.aolv.meneame.ui.view.main.fragment.NewsListFragment

/**
 * Created by antonio on 2/24/18.
 */

@Module
abstract class MainFragmentsBuilder {

    @ContributesAndroidInjector(modules = [
        (NewsListFragment.NewsListFragmentModule::class),
        (NewsListFragment.NewsListFragmentProvider::class)])
    abstract fun contributeMainFragment(): NewsListFragment
}