package es.mnmapp.aolv.meneame.ui.view.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.custom.ObservableWebView
import es.mnmapp.aolv.meneame.ui.extensions.fadeOut


/**
 * Created by antonio on 10/27/17.
 */

class WebViewActivity : BaseActivity() {

    companion object {
        val KEY_URL = "webViewActivityKeyUrl"
        val KEY_TITLE = "webViewActivityKeyTitle"

        fun createIntent(parent: BaseActivity, url: String, title: String): Intent {
            val bundle = Bundle().apply {
                putString(KEY_URL, url)
                putString(KEY_TITLE, title)
            }
            return Intent(parent, WebViewActivity::class.java).apply { putExtras(bundle) }
        }
    }

    private lateinit var webView: ObservableWebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val arguments = intent.extras
        val title = arguments.getString(KEY_TITLE)
        val url = arguments.getString(KEY_URL)

        url?.let {
            webView = findViewById(R.id.wv_container)
            progressBar = findViewById(R.id.pb_large)

            setUpWebView()
            setTitle(title)

            webView.loadUrl(url)
        } ?: finish()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

        webView.onScrollChangeListener = {
            when (progressBar.visibility) {
                View.VISIBLE -> progressBar.fadeOut()
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                when (progressBar.visibility) {
                    View.VISIBLE -> progressBar.fadeOut()
                }
            }
        }
    }
}
