package es.mnmapp.aolv.meneame.di

import android.content.res.Configuration
import es.mnmapp.aolv.data.repository.device.DeviceDataRepository
import es.mnmapp.aolv.data.repository.images.ImagesDataRepository
import es.mnmapp.aolv.data.repository.images.datasource.cloud.PlaceholdersCloudDataSource
import es.mnmapp.aolv.data.repository.images.datasource.local.PlaceholdersLocalDataSource
import es.mnmapp.aolv.data.repository.news.NewsDataRepository
import es.mnmapp.aolv.data.repository.news.datasource.cloud.NewsCloudDataSource
import es.mnmapp.aolv.domain.repository.DeviceRepository
import es.mnmapp.aolv.domain.repository.ImagesRepository
import es.mnmapp.aolv.domain.repository.NewsRepository
import es.mnmapp.aolv.domain.usecase.GetNews
import es.mnmapp.aolv.meneame.di.repositoryproviders.createLocalDatabase
import es.mnmapp.aolv.meneame.ui.Navigation
import es.mnmapp.aolv.meneame.ui.view.NavigationViewModel
import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListViewModel
import es.mnmapp.aolv.meneame.ui.view.newsviewer.NewsViewerViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

/**
 * Created by antonio on 3/18/18.
 */

// Koin context constants
const val KOIN_CONTEXT_LIST_VIEW = "listFragment"
const val KOIN_CONTEXT_NEWS_DETAIL_VIEW = "newsViewer"
// Koin beans constants
const val KOIN_BEAN_PLACEHOLDER_DB = "placeholderDb"
const val KOIN_BEAN_SCREEN_DENSITY = "screenDensity"
const val KOIN_BEAN_SCREEN_SIZE = "screenSize"
const val KOIN_BEAN_UI_THREAD = "uiThread"
const val KOIN_BEAN_WORKER_THREAD = "workerThread"

// Koin module
val mainActivityModule = applicationContext {
    bean { Navigation() }
    viewModel { NavigationViewModel(get()) }

    context(KOIN_CONTEXT_LIST_VIEW) {
        viewModel { NewsListViewModel(get(), get()) }

        // News data repository
        bean { NewsCloudDataSource(get(), get()) }
        bean<NewsRepository> { NewsDataRepository(get()) }

        // Images repository
        bean { PlaceholdersCloudDataSource(get(), get()) }
        bean(KOIN_BEAN_PLACEHOLDER_DB) { createLocalDatabase(androidApplication()).placeholdersDao() }
        bean { PlaceholdersLocalDataSource(get(), get(KOIN_BEAN_PLACEHOLDER_DB)) }
        bean<ImagesRepository> { ImagesDataRepository(get(), get()) }

        // Device repository
        bean(KOIN_BEAN_SCREEN_SIZE) {
            androidApplication().baseContext.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        }
        bean(KOIN_BEAN_SCREEN_DENSITY) {
            androidApplication().baseContext.resources.displayMetrics.density
        }
        bean<DeviceRepository> {
            DeviceDataRepository(get(KOIN_BEAN_SCREEN_DENSITY), get(KOIN_BEAN_SCREEN_SIZE))
        }

        bean { GetNews(get(KOIN_BEAN_UI_THREAD), get(KOIN_BEAN_WORKER_THREAD), get(), get(), get()) }

        context(KOIN_CONTEXT_NEWS_DETAIL_VIEW) {
            viewModel { NewsViewerViewModel(get(), get()) }
        }
    }
}