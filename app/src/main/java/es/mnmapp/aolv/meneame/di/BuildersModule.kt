package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.mnmapp.aolv.meneame.di.scopes.PerActivity
import es.mnmapp.aolv.meneame.ui.view.main.MainActivity
import es.mnmapp.aolv.meneame.ui.view.main.MainActivityModule
import es.mnmapp.aolv.meneame.ui.view.main.fragment.MainFragmentProvider
import es.mnmapp.aolv.meneame.ui.view.webview.WebViewActivity
import es.mnmapp.aolv.meneame.ui.view.webview.fragment.WebViewFragmentModule
import es.mnmapp.aolv.meneame.ui.view.webview.fragment.WebViewFragmentProvider

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Module abstract class BuildersModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, MainFragmentProvider::class))
    abstract fun bindMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(WebViewFragmentModule::class, WebViewFragmentProvider::class))
    abstract fun bindWebViewActivity(): WebViewActivity
}
