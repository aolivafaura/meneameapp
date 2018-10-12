package es.mnmapp.aolv.meneame.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.data.EndpointUrls
import es.mnmapp.aolv.data.database.LocalDatabase
import es.mnmapp.aolv.data.repository.images.datasource.local.PlaceholdersRoomDao
import es.mnmapp.aolv.data.repository.news.datasource.cloud.MeneameService
import es.mnmapp.aolv.meneame.config.CACHE_FILE_NAME
import es.mnmapp.aolv.meneame.config.CACHE_SIZE
import es.mnmapp.aolv.meneame.config.LOCAL_DATABASE_NAME
import es.mnmapp.aolv.meneame.config.NETWORK_CONNECT_TIMEOUT
import es.mnmapp.aolv.meneame.config.NETWORK_READ_TIMEOUT
import es.mnmapp.aolv.meneame.config.NETWORK_WRITE_TIMEOUT
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [(InterceptorsModule::class)])
class NetworkModule {

    /**
     * Creates and provides directory to store cached http responses.
     *
     * @param[file] Target file
     *
     * @return created cache directory
     */
    @Provides
    @Singleton
    fun provideCacheDirectory(context: Context): File = File(context.cacheDir, CACHE_FILE_NAME)

    @Provides
    @Singleton
    fun provideHttpClient(
        httpCacheDirectory: File,
        interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(NETWORK_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(NETWORK_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(NETWORK_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .cache(Cache(httpCacheDirectory, CACHE_SIZE))

        for (interceptor in interceptors) {
            builder.addNetworkInterceptor(interceptor)
        }

        return builder.build()
    }

    /**
     * Creates and provides local database
     *
     * @param[context]
     *
     * @return local database
     */
    @Provides
    @Singleton
    fun providesLocalDatabase(context: Context): LocalDatabase =
        Room.databaseBuilder(context, LocalDatabase::class.java, LOCAL_DATABASE_NAME).build()

    /**
     * Creates and provides local database
     *
     * @param[context]
     *
     * @return local database
     */
    @Provides
    @Singleton
    fun providesPlaceholderRoomDao(localDatabase: LocalDatabase): PlaceholdersRoomDao =
        localDatabase.placeholdersDao()

    /**
     * Creates and provides meneame cloud service
     *
     * @param[okHttpClient] http client
     *
     * @return meneame cloud service
     */
    @Provides
    @Singleton
    fun provideMeneameService(okHttpClient: OkHttpClient): MeneameService =
        MeneameService.create(okHttpClient, EndpointUrls.baseUrl)

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()
}