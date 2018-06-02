package es.mnmapp.aolv.meneame.ui.extensions

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.webkit.URLUtil
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.constants.EndpointUrls
import es.mnmapp.aolv.meneame.utils.*


/**
 * Created by antonio on 10/28/17.
 */

fun View.fadeIn() {
    fadeIn(300)
}

fun View.fadeIn(duration: Long) {
    fade(duration, 0f, 1f)
}

fun View.fadeOut() {
    fadeOut(300)
}

fun View.fadeOut(duration: Long) {
    fade(duration, 1f, 0f)
}

fun View.fade(duration: Long, fromAlpha: Float, toAlpha: Float) {
    animation = AlphaAnimation(fromAlpha, toAlpha)
    animation.duration = duration
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}

        override fun onAnimationStart(animation: Animation?) {
            visibility = if (toAlpha == 0f) View.INVISIBLE else View.VISIBLE
        }

        override fun onAnimationEnd(animation: Animation?) {}
    })
    startAnimation(animation)
}

fun ImageView.loadUrl(url: String?,
                      callbackSuccess: (() -> Unit)? = null,
                      callbackError: (() -> Unit)? = null) {
    url?.let {
        if (URLUtil.isValidUrl(url)) {
            Picasso.get().load(url).into(this, object : Callback {
                override fun onSuccess() {
                    callbackSuccess?.invoke()
                }

                override fun onError(e: Exception?) {
                    callbackError?.invoke()
                }
            })
        } else {
            setImageResource(0)
        }
    } ?: setImageResource(0)
}

fun ImageView.loadLogo(url: String?) {
    url?.let {
        val logoUrl = "${EndpointUrls.logoApiUrl}$url"
        if (URLUtil.isValidUrl(logoUrl)) {
            Picasso.get().load(logoUrl).into(this)
        } else {
            setImageResource(0)
        }
    } ?: setImageResource(0)
}

fun ImageView.getColorsSet(callback: ((titleColor: Int, backgroundColor: Int) -> Unit)) {
    fun handlePaletteSwatch(paletteSwatch: Palette.Swatch) {
        val titleColor = getComplementaryColor(paletteSwatch.titleTextColor)
        val background = getContrastVersionForColor(titleColor)

        callback.invoke(
                Color.parseColor(toHex(titleColor)),
                Color.parseColor(toHexWithAlpha(background, ALPHA_75))
        )
    }

    fun handleError() {
        callback.invoke(R.color.defaultListTextColor, R.color.defaultListTextBackgroundColor)
    }

    post {
        drawable?.let {
            val bitmap = (it as BitmapDrawable).bitmap
            Bitmap@ bitmap?.let {
                Palette.from(it).generate {
                    Bitmap@ it.dominantSwatch?.let { handlePaletteSwatch(it) } ?: handleError()
                }
            } ?: handleError()
        } ?: handleError()
    }
}
