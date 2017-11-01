package es.mnmapp.aolv.meneame.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.meneame.interceptors.CacheInterceptor
import okhttp3.Interceptor
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 28/07/2017.
 */

@Module class NetworkInterceptorsModule {

    @Provides @Singleton
    fun provideNetworkInterceptors(interceptor: CacheInterceptor) : ArrayList<Interceptor> {

        return arrayListOf(interceptor, getStethoInterceptor())
    }

    private fun getStethoInterceptor() = StethoInterceptor()
}