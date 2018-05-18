package es.mnmapp.aolv.meneame.utils

import android.webkit.URLUtil

object Validator {

    fun isValidUrl(url: String?) = URLUtil.isValidUrl(url)
}