package es.mnmapp.aolv.meneame.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListFragment
import es.mnmapp.aolv.meneame.ui.view.newsviewer.NewsViewerFragment

@Module
abstract class FragmentBuilderModule {

//    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun contributeMainFragment(): NewsListFragment

//    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun contributeNewsViewerFragment(): NewsViewerFragment
}
