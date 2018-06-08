/*
 *     Copyright 2018 @ https://github.com/aolivafaura
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.mnmapp.aolv.meneame.di

import android.content.res.Configuration
import es.mnmapp.aolv.data.database.LocalDatabase
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
import es.mnmapp.aolv.meneame.ui.Navigation
import es.mnmapp.aolv.meneame.ui.view.NavigationViewModel
import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListViewModel
import es.mnmapp.aolv.meneame.ui.view.newsviewer.NewsViewerViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext


// Koin context constants
const val KOIN_CONTEXT_LIST_VIEW = "listFragment"
const val KOIN_CONTEXT_NEWS_DETAIL_VIEW = "newsViewer"
// Koin beans constants
const val KOIN_BEAN_PLACEHOLDER_DB = "placeholderDb"
const val KOIN_BEAN_SCREEN_DENSITY = "screenDensity"
const val KOIN_BEAN_SCREEN_SIZE = "screenSize"

/**
 * View beans definition
 */
val viewsModule = applicationContext {
    bean {
        Navigation()
    }
    viewModel {
        NavigationViewModel(navigation = get())
    }

    // News list fragment definitions
    context(KOIN_CONTEXT_LIST_VIEW) {
        viewModel {
            NewsListViewModel(getNews = get(), connectivity = get())
        }

        // News data repository
        bean { NewsCloudDataSource(idlingResource = get(), meneameService = get()) }
        bean<NewsRepository> { NewsDataRepository(newsCloudDataSource = get()) }

        // Images repository
        bean { PlaceholdersCloudDataSource(idlingResource = get(), firestoneDb = get()) }
        bean(KOIN_BEAN_PLACEHOLDER_DB) { get<LocalDatabase>().placeholdersDao() }
        bean {
            PlaceholdersLocalDataSource(
                    idlingResource = get(),
                    placeholdersRoomDao = get(KOIN_BEAN_PLACEHOLDER_DB)
            )
        }
        bean<ImagesRepository> {
            ImagesDataRepository(
                    placeholdersCloudDataSource = get(),
                    placeholdersLocalDataSource = get()
            )
        }

        // Device repository
        bean(KOIN_BEAN_SCREEN_SIZE) {
            androidApplication().baseContext.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        }
        bean(KOIN_BEAN_SCREEN_DENSITY) {
            androidApplication().baseContext.resources.displayMetrics.density
        }
        bean<DeviceRepository> { DeviceDataRepository(androidApplication().baseContext.resources) }

        bean {
            GetNews(
                    postExecutionThread = get(KOIN_BEAN_UI_THREAD),
                    workerThread = get(KOIN_BEAN_WORKER_THREAD),
                    newsRepository = get(),
                    imagesRepository = get(),
                    deviceRepository = get()
            )
        }

        // News detail fragment definitions
        context(KOIN_CONTEXT_NEWS_DETAIL_VIEW) {
            viewModel { NewsViewerViewModel(validator = get(), connectivity = get()) }
        }
    }
}