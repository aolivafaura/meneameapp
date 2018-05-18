package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.ui.extensions.initFragment
import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListFragment
import es.mnmapp.aolv.meneame.ui.view.newsviewer.NewsViewerFragment

class Navigation {

    fun navigateToNewsList(activity: BaseActivity) {
        activity.initFragment(NewsListFragment(), addToBackStack = true)
    }

    fun navigateToNewsDetail(activity: BaseActivity, newUi: NewUi) {
        val bundle = Bundle().apply {
            putString(NewsViewerFragment.KEY_URL, newUi.url)
            putString(NewsViewerFragment.KEY_TITLE, newUi.title)
        }

        val fragment = NewsViewerFragment().apply { arguments = bundle }

        activity.initFragment(fragment, addToBackStack = true)
    }
}