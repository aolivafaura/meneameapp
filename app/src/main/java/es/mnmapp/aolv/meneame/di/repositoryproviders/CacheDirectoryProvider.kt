package es.mnmapp.aolv.meneame.di.repositoryproviders

import java.io.File

/**
 * Created by antonio on 3/11/18.
 */

private const val CACHE_FILE_NAME = "responses"

fun createCacheDirectory(file: File): File = File(file, CACHE_FILE_NAME)