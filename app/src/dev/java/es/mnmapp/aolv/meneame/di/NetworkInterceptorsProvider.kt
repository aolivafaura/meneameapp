package es.mnmapp.aolv.meneame.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import es.mnmapp.aolv.meneame.interceptors.LocalCacheInterceptor
import es.mnmapp.aolv.meneame.utils.Connectivity
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by antonio on 3/11/18.
 */

fun getNetworkInterceptors(connectivity: Connectivity) =
        arrayListOf(getLocalCacheInterceptor(connectivity), getStethoInterceptor(), getLoggingInterceptor())

private fun getLocalCacheInterceptor(connectivity: Connectivity) = LocalCacheInterceptor(connectivity)

private fun getStethoInterceptor() = StethoInterceptor()

private fun getLoggingInterceptor() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
