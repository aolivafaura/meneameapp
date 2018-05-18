package es.mnmapp.aolv.meneame.interceptors

import es.mnmapp.aolv.meneame.utils.Connectivity
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Created by antonio on 11/1/17.
 */

class LocalCacheInterceptor(private val connectivity: Connectivity) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val cacheBuilder = CacheControl.Builder()
        cacheBuilder.maxAge(0, TimeUnit.SECONDS)
        cacheBuilder.maxStale(365, TimeUnit.DAYS)
        val cacheControl = cacheBuilder.build()

        var request = chain!!.request()
        if (connectivity.isConnected()) {
            request = request.newBuilder().cacheControl(cacheControl).build()
        }

        val response = chain.proceed(request)
        return if (connectivity.isConnected()) {
            val maxAge = 30 // Cache lifetime: 30 seconds
            response.newBuilder().header("Cache-Control",
                    "public, max-age=" + maxAge).build()
        }
        else {
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            response.newBuilder().header("Cache-Control",
                    "public, only-if-cached, max-stale=" + maxStale).build()
        }
    }
}