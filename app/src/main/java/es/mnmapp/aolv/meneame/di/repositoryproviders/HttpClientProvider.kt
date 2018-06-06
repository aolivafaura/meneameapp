package es.mnmapp.aolv.meneame.di.repositoryproviders

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by antonio on 3/11/18.
 */

fun createOkHttpClient(httpCacheDirectory: File, interceptors: ArrayList<Interceptor>): OkHttpClient {
    val cacheSize = 10L * 1024 * 1024 // 10 MiB
    val cache = Cache(httpCacheDirectory, cacheSize)

    val builder = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .cache(cache)

    for (interceptor in interceptors) {
        builder.addNetworkInterceptor(interceptor)
    }

    return builder.build()
}