package es.mnmapp.aolv.meneame.dagger

import dagger.Binds
import dagger.Module
import es.mnmapp.aolv.data.repository.device.DeviceDataRepository
import es.mnmapp.aolv.data.repository.images.ImagesDataRepository
import es.mnmapp.aolv.data.repository.news.NewsDataRepository
import es.mnmapp.aolv.domain.repository.DeviceRepository
import es.mnmapp.aolv.domain.repository.ImagesRepository
import es.mnmapp.aolv.domain.repository.NewsRepository

@Module(includes = [(NetworkModule::class)])
abstract class RepositoryModule {

    @Binds
    abstract fun provideNewsRepository(newsDataRepository: NewsDataRepository): NewsRepository

    @Binds
    abstract fun provideDeviceRepository(deviceDataRepository: DeviceDataRepository): DeviceRepository

    @Binds
    abstract fun provideImagesRepository(imagesDataRepository: ImagesDataRepository): ImagesRepository
}