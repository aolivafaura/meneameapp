package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.data.repository.NewsDataRepo
import es.mnmapp.aolv.domain.repository.NewsRepo
import es.mnmapp.aolv.domain.usecase.GetPopularNews
import es.mnmapp.aolv.meneame.ui.Navigation
import es.mnmapp.aolv.meneame.ui.view.main.NavigationViewModel
import es.mnmapp.aolv.meneame.ui.view.main.fragment.NewsListViewModel
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

        bean { NewsDataRepo(get()) as NewsRepo }
        bean { GetPopularNews(get("uiThread"), get("workerThread"), get()) }
    }
}