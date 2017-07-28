package es.mnmapp.aolv.meneame.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.meneame.utils.Connectivity
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 26/07/2017.
 */
@Module class HttpClientModule {

    @Provides @Singleton fun providesHttpClient(context : Context, connectivity : Connectivity) : OkHttpClient {

        val REWRITE_CACHE_CONTROL_INTERCEPTOR : Interceptor = Interceptor { chain ->

            val cacheBuilder = CacheControl.Builder()
            cacheBuilder.maxAge(0, TimeUnit.SECONDS)
            cacheBuilder.maxStale(365, TimeUnit.DAYS)
            val cacheControl = cacheBuilder.build()

            var request = chain.request()
            if (connectivity.isConnected()) {
                request = request.newBuilder().cacheControl(cacheControl).build()
            }
            val originalResponse = chain.proceed(request)
            if (connectivity.isConnected()) {
                val maxAge = 30 // Cache lifetime: 30 seconds
                originalResponse.newBuilder().header("Cache-Control",
                                                     "public, max-age=" + maxAge).build()
            }
            else {
                val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                originalResponse.newBuilder().header("Cache-Control",
                                                     "public, only-if-cached, max-stale=" + maxStale).build()
            }
        }

        val httpCacheDirectory = File(context.cacheDir, "responses")
        val cacheSize = 10L * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)

        return OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build()
    }
}
