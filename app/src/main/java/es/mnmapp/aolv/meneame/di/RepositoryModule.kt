package es.mnmapp.aolv.meneame.di

import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.data.net.MeneameService
import es.mnmapp.aolv.data.repository.NewsDataRepo
import es.mnmapp.aolv.domain.repository.NewsRepo
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Module(includes = [(ApiModule::class)])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMeneosRepo(meneameService: MeneameService): NewsRepo = NewsDataRepo(meneameService)
}
