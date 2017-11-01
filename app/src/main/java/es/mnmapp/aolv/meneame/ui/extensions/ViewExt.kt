package es.mnmapp.aolv.meneame.ui.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

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
        override fun onAnimationRepeat(animation: Animation?) {
        }

        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            visibility = if (toAlpha == 0f) View.GONE else View.VISIBLE
        }
    })
    startAnimation(animation)
}