package es.mnmapp.aolv.meneame.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.meneame.interceptors.LocalCacheInterceptor
import es.mnmapp.aolv.meneame.utils.Connectivity
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 28/07/2017.
 */

@Module
class NetworkInterceptorsModule {

    @Provides
    @Singleton
    fun provideNetworkInterceptors(connectivity: Connectivity): ArrayList<Interceptor> {
        return arrayListOf(getLocalCacheInterceptor(connectivity), getStethoInterceptor(), getLoggingInterceptor())
    }

    private fun getLocalCacheInterceptor(connectivity: Connectivity) = LocalCacheInterceptor(connectivity)

    private fun getStethoInterceptor() = StethoInterceptor()

    private fun getLoggingInterceptor() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
}
