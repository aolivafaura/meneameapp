package es.mnmapp.aolv.meneame.ui.extensions

import android.webkit.URLUtil
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by antonio on 10/29/17.
 */


fun ImageView.loadUrl(url: String?) {
    url?.let {
        if (URLUtil.isValidUrl(url)) {
            Picasso.get().load(url).into(this)
        }
    }
}