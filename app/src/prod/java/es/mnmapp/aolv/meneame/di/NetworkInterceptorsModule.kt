package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.meneame.utils.Connectivity
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 28/07/2017.
 */

@Module class NetworkInterceptorsModule {

    @Provides @Singleton
    fun provideNetworkInterceptors(connectivity : Connectivity) : ArrayList<Interceptor> {

        return arrayListOf(getCacheInterceptor(connectivity))
    }

    fun getCacheInterceptor(connectivity : Connectivity) = Interceptor { chain ->

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
}