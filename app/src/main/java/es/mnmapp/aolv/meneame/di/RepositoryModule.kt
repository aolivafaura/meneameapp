package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.data.net.MeneameService
import es.mnmapp.aolv.data.repository.MeneosDataRepo
import es.mnmapp.aolv.domain.repository.MeneosRepo
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Module class RepositoryModule {

    @Provides
    @Singleton
    fun provideMeneosRepo(meneameService : MeneameService) : MeneosRepo = MeneosDataRepo(meneameService)
}
