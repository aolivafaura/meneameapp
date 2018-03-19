package es.mnmapp.aolv.meneame.koin.repositoryproviders

import java.io.File

/**
 * Created by antonio on 3/11/18.
 */

fun createCacheDirectory(file: File) = File(file, "responses")