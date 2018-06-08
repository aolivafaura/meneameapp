/*
 *     Copyright 2018 @ https://github.com/aolivafaura/
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

package es.mnmapp.aolv.meneame.ui.view.newslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.entity.Section
import es.mnmapp.aolv.domain.usecase.GetNews
import es.mnmapp.aolv.meneame.connectivity.Connectivity
import es.mnmapp.aolv.meneame.entity.NewCellUi
import es.mnmapp.aolv.meneame.entity.fromNewToNewCellUi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 * View model for news list
 *
 * @see NewsListFragment
 */
class NewsListViewModel(
    private val getNews: GetNews,
    private val connectivity: Connectivity
) : ViewModel() {

    // Fields -----

    val news = MutableLiveData<List<NewCellUi>>()
    val state = MutableLiveData<ViewState>()
    val connectivityState = MutableLiveData<Connectivity.State>()

    private val disposables = CompositeDisposable()

    // Initialization -----

    init {
        observeConnectivity()
    }

    private fun observeConnectivity() {
        connectivity.observeConnectivity()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivityState.value = it }
            ?.let { disposables.addAll(it) }
    }

    // ViewModel overrides -----

    override fun onCleared() {
        super.onCleared()
        getNews.clear()
        disposables.clear()
    }

    // Class methods -----

    fun fetchNews() {
        val successHandler: ((List<New>) -> Unit) = {
            state.value = ViewState.Idle
            news.value = it.map { fromNewToNewCellUi(it) }
        }
        val errorHandler: ((Throwable) -> Unit) = {
            state.value = ViewState.Idle
        }

        state.value = ViewState.Refreshing
        getNews.execute(Section.Popular, successHandler, errorHandler)
    }

    // Inner classes -----

    enum class ViewState { Idle, Refreshing }
}
