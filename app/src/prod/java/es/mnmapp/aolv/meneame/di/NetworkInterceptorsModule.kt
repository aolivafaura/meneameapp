package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.meneame.interceptors.CacheInterceptor
import es.mnmapp.aolv.meneame.utils.Connectivity
import okhttp3.Interceptor
import javax.inject.Singleton


/**
 * Created by antoniojoseoliva on 28/07/2017.
 */

@Module
class NetworkInterceptorsModule {

    @Provides
    @Singleton
    fun provideNetworkInterceptors(connectivity: Connectivity): ArrayList<Interceptor> {

        return arrayListOf(CacheInterceptor(connectivity))
    }
}