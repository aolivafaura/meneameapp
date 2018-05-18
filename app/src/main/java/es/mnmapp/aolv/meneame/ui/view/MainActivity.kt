package es.mnmapp.aolv.meneame.ui.view

import android.os.Bundle
import es.mnmapp.aolv.meneame.ui.BaseActivity
import org.koin.android.architecture.ext.viewModel

class MainActivity : BaseActivity() {

    // Fields -----
    private val mainViewModel by viewModel<NavigationViewModel>()

    // Activity overrides -----
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            mainViewModel.navigateToNewsList(this)
        }
    }
}
