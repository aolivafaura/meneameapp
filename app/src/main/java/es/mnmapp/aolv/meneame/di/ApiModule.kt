package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.data.net.MeneameService

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

@Module class ApiModule {

    @Provides fun provideMeneameService() = MeneameService.create()
}
