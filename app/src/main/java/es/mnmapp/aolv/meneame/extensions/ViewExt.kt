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

@file:Suppress("unused")

package es.mnmapp.aolv.meneame.extensions

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.utils.ColorAlpha
import es.mnmapp.aolv.meneame.utils.Validator
import es.mnmapp.aolv.meneame.utils.getComplementaryColor
import es.mnmapp.aolv.meneame.utils.getContrastVersionForColor
import es.mnmapp.aolv.meneame.utils.colorToHex
import es.mnmapp.aolv.meneame.utils.colorToHexWithAlpha

/**
 * Applies fade in effect
 *
 * @param[duration] Animation duration. Default 300 milliseconds
 */
fun View.fadeIn(duration: Long = 300L) {
    fade(duration, 0f, 1f)
}

/**
 * Applies fade out effect
 *
 * @param[duration] Animation duration. Default 300 milliseconds
 */
fun View.fadeOut(duration: Long = 300L) {
    fade(duration, 1f, 0f)
}

/**
 * Applies fade effect
 *
 * @param[duration] Animation duration. Default 300 milliseconds
 * @param[fromAlpha] Initial view alpha
 * @param[toAlpha] Final view alpha
 */
fun View.fade(duration: Long, fromAlpha: Float, toAlpha: Float) {
    animation = AlphaAnimation(fromAlpha, toAlpha).apply {
        this.duration = duration
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                visibility = if (toAlpha == 0f) View.INVISIBLE else View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {}
        })

        startAnimation(this)
    }
}

/**
 * Loads given url safely. Url format validation is applied.
 *
 * @param[url] Image url to load
 * @param[actionSuccess] Action to execute when image is loaded successfully
 * @param[actionError] Action to execute when load fails
 */
inline fun ImageView.loadUrl(
    url: String?,
    crossinline actionSuccess: (() -> Unit) = {},
    crossinline actionError: (() -> Unit) = {}
) {
    if (Validator.isValidUrl(url)) {
        Picasso.get().load(url).into(this, object : Callback {
            override fun onSuccess() {
                actionSuccess.invoke()
            }

            override fun onError(e: Exception?) {
                actionError.invoke()
            }
        })
    } else {
        setImageResource(0)
    }
}

/**
 * Analyze view bitmap and extracts proper colors title and background color to mask it
 *
 * @param[callback] Function to notify extracted colors
 */
inline fun ImageView.getColorsSet(
    crossinline callback: ((titleColor: Int, backgroundColor: Int) -> Unit)
) {
    post {
        drawable?.let {
            val bitmap = (it as BitmapDrawable).bitmap
            Bitmap@ bitmap?.let {
                Palette.from(it).generate {
                    Bitmap@ it.dominantSwatch?.let {
                        handlePaletteSwatch(it, callback)
                    } ?: handleError(callback)
                }
            } ?: handleError(callback)
        } ?: handleError(callback)
    }
}

@PublishedApi
internal inline fun handlePaletteSwatch(
    paletteSwatch: Palette.Swatch,
    crossinline callback: ((titleColor: Int, backgroundColor: Int) -> Unit)
) {
    val titleColor = getComplementaryColor(paletteSwatch.titleTextColor)
    val background = getContrastVersionForColor(titleColor)

    callback.invoke(
        Color.parseColor(colorToHex(titleColor)),
        Color.parseColor(colorToHexWithAlpha(background, ColorAlpha.Alpha70))
    )
}

@PublishedApi
internal inline fun handleError(
    crossinline callback: ((titleColor: Int, backgroundColor: Int) -> Unit)
) {
    callback.invoke(R.color.defaultListTextColor, R.color.defaultListTextBackgroundColor)
}
