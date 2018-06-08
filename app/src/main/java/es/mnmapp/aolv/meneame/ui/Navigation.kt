package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import es.mnmapp.aolv.meneame.extensions.initFragment
import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListFragment
import es.mnmapp.aolv.meneame.ui.view.newsviewer.NewsViewerFragment

class Navigation {

    // Class methods -----
    fun navigateToNewsList(activity: BaseActivity) {
        activity.initFragment(NewsListFragment(), addToBackStack = true)
    }

    fun navigateToNewsDetail(activity: BaseActivity, url: String?, title: String?) {
        val bundle = Bundle().apply {
            putString(NewsViewerFragment.KEY_URL, url)
            putString(NewsViewerFragment.KEY_TITLE, title)
        }

        val fragment = NewsViewerFragment().apply { arguments = bundle }
        activity.initFragment(fragment, addToBackStack = true)
    }
}