package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.ui.extensions.initFragment
import es.mnmapp.aolv.meneame.ui.view.main.fragment.NewsListFragment
import es.mnmapp.aolv.meneame.ui.view.webview.fragment.WebViewFragment

class Navigation {

    fun navigateToNewsList(activity: BaseActivity) {
        activity.initFragment(NewsListFragment(), addToBackStack = true)
    }

    fun navigateToNewsDetail(activity: BaseActivity, newUi: NewUi) {
        val bundle = Bundle().apply {
            putString(WebViewFragment.KEY_URL, newUi.url)
            putString(WebViewFragment.KEY_TITLE, newUi.title)
        }

        val fragment = WebViewFragment().apply { arguments = bundle }

        activity.initFragment(fragment, addToBackStack = true)
    }
}