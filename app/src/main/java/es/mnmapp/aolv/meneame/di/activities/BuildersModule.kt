package es.mnmapp.aolv.meneame.di.activities

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.mnmapp.aolv.meneame.di.activities.main.MainFragmentsBuilder
import es.mnmapp.aolv.meneame.di.activities.webview.WebViewFragmentsBuilder
import es.mnmapp.aolv.meneame.ui.view.main.MainActivity
import es.mnmapp.aolv.meneame.ui.view.webview.WebViewActivity
import es.mnmapp.aolv.meneame.ui.view.webview.fragment.WebViewFragment

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [
        (MainActivity.MainActivityModule::class),
        (MainFragmentsBuilder::class)])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [
        (WebViewFragment.WebViewFragmentModule::class),
        (WebViewFragmentsBuilder::class)])
    abstract fun bindWebViewActivity(): WebViewActivity
}
