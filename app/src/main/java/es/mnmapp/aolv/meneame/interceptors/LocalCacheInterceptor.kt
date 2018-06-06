package es.mnmapp.aolv.meneame.interceptors

import es.mnmapp.aolv.meneame.connectivity.Connectivity
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Created by antonio on 11/1/17.
 */

class LocalCacheInterceptor(
        private val connectivity: Connectivity,
        private val cacheMaxAge: Int,
        private val cacheMaxStale: Int
) : Interceptor {

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
            val maxAge = cacheMaxAge // Cache lifetime in seconds
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
        } else {
            val maxStale = cacheMaxStale // Stale in seconds
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }
    }
}