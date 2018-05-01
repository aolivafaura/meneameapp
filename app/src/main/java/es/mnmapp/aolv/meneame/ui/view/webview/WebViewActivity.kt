package es.mnmapp.aolv.meneame.ui.view.webview

import android.content.Intent
import android.os.Bundle
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.BaseViewModel
import es.mnmapp.aolv.meneame.ui.view.webview.fragment.WebViewFragment
import es.mnmapp.aolv.meneame.ui.view.webview.fragment.WebViewFragment.Companion.KEY_TITLE
import es.mnmapp.aolv.meneame.ui.view.webview.fragment.WebViewFragment.Companion.KEY_URL


/**
 * Created by antonio on 10/27/17.
 */

class WebViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragment(WebViewFragment.newInstance(intent.getStringExtra(KEY_URL), intent.getStringExtra(KEY_TITLE)))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getViewModels(): List<BaseViewModel> = emptyList()

    companion object {
        fun createIntent(parent: BaseActivity, url: String, title: String): Intent {
            val bundle = Bundle().apply {
                putString(KEY_URL, url)
                putString(KEY_TITLE, title)
            }
            return Intent(parent, WebViewActivity::class.java).apply { putExtras(bundle) }
        }
    }
}
