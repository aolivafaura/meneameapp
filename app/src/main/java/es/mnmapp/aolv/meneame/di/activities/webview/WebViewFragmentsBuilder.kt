package es.mnmapp.aolv.meneame.di.activities.webview

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.mnmapp.aolv.meneame.ui.view.webview.fragment.WebViewFragment

/**
 * Created by antonio on 2/24/18.
 */

@Module
abstract class WebViewFragmentsBuilder {

    @ContributesAndroidInjector
    abstract fun contributeWebViewFragment(): WebViewFragment
}