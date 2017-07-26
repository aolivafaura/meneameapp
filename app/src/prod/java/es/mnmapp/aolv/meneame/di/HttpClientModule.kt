package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

@Module class HttpClientModule {

    @Provides @Singleton fun providesHttpClient() =
            OkHttpClient
                    .Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .build()
}
