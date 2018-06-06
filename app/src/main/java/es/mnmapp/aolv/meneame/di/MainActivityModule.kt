package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.data.repository.news.NewsDataRepository
import es.mnmapp.aolv.data.repository.news.datasource.cloud.NewsCloudDataSource
import es.mnmapp.aolv.data.repository.images.ImagesDataRepository
import es.mnmapp.aolv.data.repository.images.datasource.cloud.PlaceholdersCloudDataSource
import es.mnmapp.aolv.data.repository.images.datasource.local.PlaceholdersLocalDataSource
import es.mnmapp.aolv.domain.repository.NewsRepository
import es.mnmapp.aolv.domain.repository.ImagesRepository
import es.mnmapp.aolv.domain.usecase.GetNews
import es.mnmapp.aolv.meneame.ui.Navigation
import es.mnmapp.aolv.meneame.ui.view.NavigationViewModel
import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListViewModel
import es.mnmapp.aolv.meneame.ui.view.newsviewer.NewsViewerViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

/**
 * Created by antonio on 3/18/18.
 */

// Koin module
val mainActivityModule = applicationContext {
    bean { Navigation() }
    viewModel { NavigationViewModel(get()) }

    context("listFragment") {
        viewModel { NewsListViewModel(get()) }

        bean { NewsCloudDataSource(get(), get()) }
        bean<NewsRepository> { NewsDataRepository(get()) }

        bean<ImagesRepository> { ImagesDataRepository(get(), get()) }
        bean { PlaceholdersCloudDataSource(get(), get()) }
        bean { PlaceholdersLocalDataSource(get(), get("placeholdersDb")) }

        bean { GetNews(get("uiThread"), get("workerThread"), get(), get()) }

        context("newsViewer") {
            viewModel { NewsViewerViewModel(get()) }
        }
    }
}