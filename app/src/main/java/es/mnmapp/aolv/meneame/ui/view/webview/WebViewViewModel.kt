package es.mnmapp.aolv.meneame.ui.view.webview

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.webkit.URLUtil

class WebViewViewModel : ViewModel() {

    val url = MutableLiveData<String?>()
    val title = MutableLiveData<String>()

    fun setUrl(url: String?) {
        this.url.value = if (URLUtil.isValidUrl(url)) url else null
    }

    fun setTitle(title: String?) {
        this.title.value = title ?: ""
    }
}