package es.mnmapp.aolv.meneame.koin

import es.mnmapp.aolv.data.repository.NewsDataRepo
import es.mnmapp.aolv.domain.repository.NewsRepo
import es.mnmapp.aolv.domain.usecase.GetPopularNews
import es.mnmapp.aolv.meneame.ui.view.main.MainViewModel
import es.mnmapp.aolv.meneame.ui.view.main.fragment.NewsListViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

/**
 * Created by antonio on 3/18/18.
 */

// Koin module
val mainActivityModule = applicationContext {
    viewModel { MainViewModel() }

    context("listFragment") {
        viewModel { NewsListViewModel(get()) }

        bean { NewsDataRepo(get()) as NewsRepo }
        bean { GetPopularNews(get("uiThread"), get("workerThread"), get()) }
    }
}