package es.mnmapp.aolv.meneame.ui.view.newsviewer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.meneame.utils.Validator

class NewsViewerViewModel(
    private val validator: Validator
) : ViewModel() {

    // Fields -----
    val url = MutableLiveData<String?>()
    val title = MutableLiveData<String>()

    // Class methods -----
    fun setUrl(url: String?) {
        this.url.value = if (validator.isValidUrl(url)) url else null
    }

    fun setTitle(title: String?) {
        this.title.value = title ?: ""
    }
}