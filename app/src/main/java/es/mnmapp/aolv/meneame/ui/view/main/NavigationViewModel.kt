package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.Navigation

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

class NavigationViewModel(private val navigation: Navigation) : ViewModel() {

    fun navigateToNewsList(activity: BaseActivity) {
        navigation.navigateToNewsList(activity)
    }

    fun navigateToNewsDetail(activity: BaseActivity, newUi: NewUi) {
        navigation.navigateToNewsDetail(activity, newUi)
    }
}
