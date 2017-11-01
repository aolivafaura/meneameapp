package es.mnmapp.aolv.meneame.di

import android.content.Context
import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.meneame.MnmApp
import es.mnmapp.aolv.meneame.interceptors.CacheInterceptor
import es.mnmapp.aolv.meneame.loggers.AnalitycsLogger
import es.mnmapp.aolv.meneame.utils.Connectivity
import es.mnmapp.aolv.meneame.utils.Lg
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: MnmApp): Context = app.applicationContext!!

    @Provides
    @Named("uiThread")
    fun provideUIThread(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named("workerThread")
    fun provideWorkerThread(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    fun provideConnectivityManager(context: Context): Connectivity = Connectivity(context)

    @Provides
    @Singleton
    fun provideCacheDirectory(context: Context): File = File(context.cacheDir, "responses")

    @Provides
    @Singleton
    fun provideAnalitycs(context: Context): AnalitycsLogger = AnalitycsLogger(context)

    @Provides
    @Singleton
    fun provideLogger(analitycsLogger: AnalitycsLogger): Lg = Lg(analitycsLogger)

    @Provides
    @Singleton
    fun provideCacheInterceptor(connectivity: Connectivity): CacheInterceptor = CacheInterceptor(connectivity)
}
