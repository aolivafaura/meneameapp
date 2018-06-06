package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.meneame.interceptors.LocalCacheInterceptor
import es.mnmapp.aolv.meneame.utils.Connectivity
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by antonio on 3/11/18.
 */

const val CACHE_MAX_AGE = 60 // 1 minute
const val CACHE_MAX_STALE = 60 * 60 * 24 * 28 // 4 weeks

fun getNetworkInterceptors(connectivity: Connectivity): ArrayList<Interceptor> =
        arrayListOf(getCacheInterceptor(connectivity), getLoggerInterceptor())

private fun getCacheInterceptor(connectivity: Connectivity): LocalCacheInterceptor =
        LocalCacheInterceptor(connectivity, CACHE_MAX_AGE, CACHE_MAX_STALE)

private fun getLoggerInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }