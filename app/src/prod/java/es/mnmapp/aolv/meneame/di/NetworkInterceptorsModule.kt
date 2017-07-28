package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

/**
 * Created by antoniojoseoliva on 28/07/2017.
 */

@Module class NetworkInterceptorsModule {

    @Provides
    fun provideNetworkInterceptors() : List<Interceptor> = emptyList()
}