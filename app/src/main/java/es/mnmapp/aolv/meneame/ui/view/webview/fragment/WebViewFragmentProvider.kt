package es.mnmapp.aolv.meneame.ui.view.webview.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by antonio on 11/1/17.
 */

@Module abstract class WebViewFragmentProvider {

    @ContributesAndroidInjector(modules = arrayOf(WebViewFragmentModule::class))
    abstract fun provideWebViewFragmentFactory(): WebViewFragment
}