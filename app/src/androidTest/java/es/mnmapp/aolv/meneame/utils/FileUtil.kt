package es.mnmapp.aolv.meneame.utils

import android.content.res.AssetManager

/**
 * Helper function which will load JSON from
 * the path specified
 *
 * @param filePath Path to JSON file
 * @return string file content
 */
fun AssetManager.getJson(filePath: String): String =
    this.open(filePath).bufferedReader().use { it.readText() }
