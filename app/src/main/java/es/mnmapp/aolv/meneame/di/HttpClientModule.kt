package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 26/07/2017.
 */
@Module class HttpClientModule {

    @Provides @Singleton
    fun providesHttpClient(cacheDir : File, interceptors : ArrayList<Interceptor>) : OkHttpClient {

        val httpCacheDirectory = File(cacheDir, "responses")
        val cacheSize = 10L * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)

        val builder = OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(cache)

        for (interceptor in interceptors) {
            builder.addNetworkInterceptor(interceptor)
        }

        return builder.build()
    }
}
