/*
 *     Copyright 2018 @ https://github.com/aolivafaura
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.mnmapp.aolv.meneame.ui.view.newsviewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.extensions.fadeOut
import es.mnmapp.aolv.meneame.ui.BaseFragment
import kotlinx.android.synthetic.main.web_view_fragment.*

/**
 * News detail fragment.
 * This fragment will open a web view and show the original source of information.
 */
class NewsViewerFragment : BaseFragment() {

    // Fragment overrides -----

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    private fun initObservers() {
//        webViewViewModel.url.observe(this, Observer {
//            if (it != null) {
//                webViewContainer.loadUrl(it)
//            } else {
//                activity?.supportFragmentManager?.popBackStack()
//            }
//        })
//
//        webViewViewModel.title.observe(this, Observer {
//            activity?.title = it
//        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpWebView()
//
//        webViewViewModel.setTitle(arguments?.getString(KEY_TITLE))
//        webViewViewModel.setUrl(arguments?.getString(KEY_URL))
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webViewContainer.settings.javaScriptEnabled = true
        webViewContainer.settings.setSupportZoom(true)

        nestedScrollWebView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
            when (progressBar.visibility) {
                View.VISIBLE -> progressBar.fadeOut()
            }
        }
        webViewContainer.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                when (progressBar?.visibility) {
                    View.VISIBLE -> progressBar.fadeOut()
                }
            }
        }
    }

    // BaseFragment overrides -----

    override fun getFragmentLayout() = R.layout.web_view_fragment

    override fun getAnalyticsName() = "NewsViewer"

    // Companion object -----

    companion object {
        const val KEY_URL = "webViewFragmentKeyUrl"
        const val KEY_TITLE = "webViewFragmentKeyTitle"
    }
}
