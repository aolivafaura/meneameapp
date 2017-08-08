package es.mnmapp.aolv.meneame.extensions

import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by antoniojoseoliva on 03/08/2017.
 */

fun ImageView.loadUrl(url : String?) {
    url?.let {
        if (URLUtil.isValidUrl(url)) {
            Glide.with(this.context).load(url).into(this)
        }
    }
}