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

package es.mnmapp.aolv.meneame.dagger.videmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import es.mnmapp.aolv.meneame.ui.view.NavigationViewModel
import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListViewModel
import es.mnmapp.aolv.meneame.ui.view.newsviewer.NewsViewerViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
//    @PerActivity
    @ViewModelKey(NavigationViewModel::class)
    internal abstract fun navigationViewModel(viewModel: NavigationViewModel): ViewModel

    @Binds
    @IntoMap
//    @PerFragment
    @ViewModelKey(NewsListViewModel::class)
    internal abstract fun newsListViewModel(viewModel: NewsListViewModel): ViewModel

    @Binds
    @IntoMap
//    @PerFragment
    @ViewModelKey(NewsViewerViewModel::class)
    internal abstract fun newsViewerViewModel(viewModel: NewsViewerViewModel): ViewModel
}