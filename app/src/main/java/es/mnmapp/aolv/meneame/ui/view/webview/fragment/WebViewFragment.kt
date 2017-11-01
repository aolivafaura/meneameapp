package es.mnmapp.aolv.meneame.ui.view.webview.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.ui.BaseFragment
import es.mnmapp.aolv.meneame.ui.extensions.fadeOut
import kotlinx.android.synthetic.main.web_view_fragment.*

/**
 * Created by antonio on 11/1/17.
 */

class WebViewFragment: BaseFragment() {

    override fun getFragmentLayout() = R.layout.web_view_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title = arguments?.getString(KEY_TITLE)
        val url = arguments?.getString(KEY_URL)

        url?.let {
            setUpWebView()
            activity?.title = title

            wvContainer.loadUrl(url)
        } ?: activity?.finish()
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
        val KEY_URL = "webViewFragmentKeyUrl"
        val KEY_TITLE = "webViewFragmentKeyTitle"

        fun newInstance(url: String, title: String): BaseFragment {
            val bundle = Bundle().apply {
                putString(KEY_URL, url)
                putString(KEY_TITLE, title)
            }
            return WebViewFragment().apply {
                arguments = bundle
            }
        }
    }
}