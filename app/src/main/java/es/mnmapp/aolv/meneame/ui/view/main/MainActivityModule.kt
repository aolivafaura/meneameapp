package es.mnmapp.aolv.meneame.ui.view.main

import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.domain.usecase.GetPopularMeneos

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */
@Module
class MainActivityModule {

    @Provides
    fun provideMainViewModelFactory(getPopularMeneos: GetPopularMeneos) = MainViewModelFactory(getPopularMeneos)
}
