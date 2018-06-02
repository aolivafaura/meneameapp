package es.mnmapp.aolv.meneame.utils

import android.graphics.Color

fun getComplementaryColor(colorToInvert: Int): Int {
    val hsv = FloatArray(3)
    Color.RGBToHSV(Color.red(colorToInvert), Color.green(colorToInvert), Color.blue(colorToInvert), hsv)
    hsv[0] = (hsv[0] + 180) % 360
    return Color.HSVToColor(hsv)
}

fun getContrastVersionForColor(color: Int): Int {
    val hsv = FloatArray(3)
    Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv)
    if (hsv[2] < 0.5) {
        hsv[2] = 0.7f
    } else {
        hsv[2] = 0.3f
    }
    hsv[1] = hsv[1] * 0.2f
    return Color.HSVToColor(hsv)
}

fun toHex(color: Int) = String.format("#%06X", 0xFFFFFF and color)

const val ALPHA_100 = "FF"
const val ALPHA_95 = "F2"
const val ALPHA_90 = "E6"
const val ALPHA_85 = "D9"
const val ALPHA_80 = "CC"
const val ALPHA_75 = "BF"
const val ALPHA_70 = "B3"
const val ALPHA_65 = "A6"
const val ALPHA_60 = "99"
const val ALPHA_55 = "8C"
const val ALPHA_50 = "80"
const val ALPHA_45 = "73"
const val ALPHA_40 = "66"
const val ALPHA_35 = "59"
const val ALPHA_30 = "4D"
const val ALPHA_25 = "40"
const val ALPHA_20 = "33"
const val ALPHA_15 = "26"
const val ALPHA_10 = "1A"
const val ALPHA_5 = "0D"
const val ALPHA_0 = "00"

fun toHexWithAlpha(color: Int, alpha: String) = String.format("#$alpha%06X", 0xFFFFFF and color)