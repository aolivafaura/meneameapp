package es.mnmapp.aolv.meneame.dagger

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.meneame.connectivity.Connectivity
import es.mnmapp.aolv.meneame.interceptors.LocalCacheInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

const val CACHE_MAX_AGE = 5 // 5 seconds
const val CACHE_MAX_STALE = 30 // 30 seconds

@Module
class InterceptorsModule {

    /**
     * Provides network interceptor for dev compilation.
     *
     * @param[connectivity] Connectivity
     *
     * @return list of interceptors
     */
    @Provides
    @Singleton
    fun provideNetworkInterceptors(connectivity: Connectivity): ArrayList<Interceptor> =
        arrayListOf(
            getLocalCacheInterceptor(connectivity),
            getStethoInterceptor(),
            getLoggingInterceptor()
        )

    private fun getLocalCacheInterceptor(connectivity: Connectivity): LocalCacheInterceptor =
        LocalCacheInterceptor(connectivity, CACHE_MAX_AGE, CACHE_MAX_STALE)

    private fun getStethoInterceptor(): StethoInterceptor = StethoInterceptor()

    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
}