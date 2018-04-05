package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.meneame.interceptors.CacheInterceptor
import es.mnmapp.aolv.meneame.utils.Connectivity
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by antonio on 3/11/18.
 */

fun getNetworkInterceptors(connectivity: Connectivity) =
        arrayListOf(getCacheInterceptor(connectivity), getLoggerInterceptor())

private fun getCacheInterceptor(connectivity: Connectivity) = CacheInterceptor(connectivity)

private fun getLoggerInterceptor() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }