package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.view.main.fragment.NewsListFragment
import es.mnmapp.aolv.meneame.ui.view.webview.WebViewActivity
import org.koin.android.architecture.ext.viewModel

class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            setFragment(NewsListFragment.newInstance())
        }

        observeSelectedNew()
    }

    private fun observeSelectedNew() {
        mainViewModel.selectedNew.observe(this, Observer<NewUi> {
            startActivity(WebViewActivity.createIntent(this, it!!.url!!, it.title!!))
        })
    }
}
