package es.mnmapp.aolv.meneame.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 26/07/2017.
 */
@Module class HttpClientModule {

    @Provides @Singleton fun providesHttpClient() =
            OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
}
