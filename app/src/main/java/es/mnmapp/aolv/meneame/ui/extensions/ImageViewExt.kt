package es.mnmapp.aolv.meneame.ui.extensions

import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by antonio on 10/29/17.
 */


fun ImageView.loadUrl(url: String?) {
    url?.let {
        if (URLUtil.isValidUrl(url)) {
            Glide.with(this.context).load(url).into(this)
        }
    }
}