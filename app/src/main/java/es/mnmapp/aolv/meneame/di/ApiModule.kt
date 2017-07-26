package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.data.net.MeneameService
import okhttp3.OkHttpClient

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

@Module class ApiModule {

    @Provides fun provideMeneameService(okHttpClient : OkHttpClient) = MeneameService.create(okHttpClient)
}
