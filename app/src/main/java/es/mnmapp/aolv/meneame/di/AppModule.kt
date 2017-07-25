package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.meneame.MnmApp
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Module class AppModule {

    @Provides @Singleton fun provideContext(app : MnmApp) = app

    @Provides
    @Singleton
    fun provideUIThread() : Scheduler = AndroidSchedulers.mainThread()
}
