package es.mnmapp.aolv.meneame.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import es.mnmapp.aolv.meneame.interceptors.LocalCacheInterceptor
import es.mnmapp.aolv.meneame.connectivity.Connectivity
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by antonio on 3/11/18.
 */

const val CACHE_MAX_AGE = 5 // 5 seconds
const val CACHE_MAX_STALE = 30 // 30 weeks

fun getNetworkInterceptors(connectivity: Connectivity): ArrayList<Interceptor> =
        arrayListOf(getLocalCacheInterceptor(connectivity), getStethoInterceptor(), getLoggingInterceptor())

private fun getLocalCacheInterceptor(connectivity: Connectivity): LocalCacheInterceptor =
        LocalCacheInterceptor(connectivity, CACHE_MAX_AGE, CACHE_MAX_STALE)

private fun getStethoInterceptor(): StethoInterceptor = StethoInterceptor()

private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
