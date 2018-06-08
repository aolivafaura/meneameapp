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

package es.mnmapp.aolv.meneame.utils

import android.graphics.Color

/**
 * Calculate complementary color of the given one
 *
 * @param[color] target color on #argb
 *
 * @return complementary color on #argb integer
 */
fun getComplementaryColor(color: Int): Int {
    val hsv = FloatArray(3)
    Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv)
    hsv[0] = (hsv[0] + 180) % 360
    return Color.HSVToColor(hsv)
}

/**
 * Calculate contrast color of the given one
 *
 * @param[color] target color on #argb integer
 *
 * @return contrast color on #argb integer
 */
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

/**
 * Transform #argb color integer into the string hexadecimal version
 *
 * @param[color] target color
 */
fun colorToHex(color: Int): String = String.format("#%06X", 0xFFFFFF and color)

/**
 * Transform #argb color integer into the string hexadecimal version
 *
 * @param[color] target color
 * @param[alpha] alpha to apply
 */
fun colorToHexWithAlpha(color: Int, alpha: ColorAlpha): String =
    String.format("#${alpha.value}%06X", 0xFFFFFF and color)
