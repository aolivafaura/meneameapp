package es.mnmapp.aolv.meneame.ui.view.webview

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.ui.BaseFragment
import es.mnmapp.aolv.meneame.ui.extensions.fadeOut
import kotlinx.android.synthetic.main.web_view_fragment.*
import org.koin.android.ext.android.inject

/**
 * Created by antonio on 11/1/17.
 */

class WebViewFragment : BaseFragment() {

    private val webViewViewModel by inject<WebViewViewModel>()

    override fun getFragmentLayout() = R.layout.web_view_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpWebView()

        webViewViewModel.setTitle(arguments?.getString(KEY_TITLE))
        webViewViewModel.setUrl(arguments?.getString(KEY_URL))
    }

    private fun initObservers() {
        webViewViewModel.url.observe(this, Observer {
            if (it != null) {
                wvContainer.loadUrl(it)
            } else {
                activity?.supportFragmentManager?.popBackStack()
            }
        })

        webViewViewModel.title.observe(this, Observer {
            activity?.title = it
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        wvContainer.settings.javaScriptEnabled = true
        wvContainer.settings.setSupportZoom(true)

        wvContainer.onScrollChangeListener = {
            when (progressBar.visibility) {
                View.VISIBLE -> progressBar.fadeOut()
            }
        }

        wvContainer.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                when (progressBar?.visibility) {
                    View.VISIBLE -> progressBar.fadeOut()
                }
            }
        }
    }

    companion object {
        const val KEY_URL = "webViewFragmentKeyUrl"
        const val KEY_TITLE = "webViewFragmentKeyTitle"
    }
}