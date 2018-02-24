package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.usecase.GetPopularNews
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.entity.mapper.fromNewToNewUi
import es.mnmapp.aolv.meneame.rx.BaseObserver
import es.mnmapp.aolv.meneame.ui.view.common.ViewState

/**
 * Created by antonio on 2/24/18.
 */

class NewsListViewModel(private val getPopularNews: GetPopularNews) : ViewModel() {

    val news = MutableLiveData<MutableList<NewUi>>()
    val state = MutableLiveData<ViewState>()

    fun loadNews() {
        state.value = ViewState.Refreshing

        getPopularNews.execute(object : BaseObserver<List<New>>() {

            override fun onError(error: Throwable) {
                super.onError(error)
                state.value = ViewState.Idle
            }

            override fun onNext(result: List<New>) {
                state.value = ViewState.Idle
                news.value = result.map { fromNewToNewUi(it) }.toMutableList()
            }
        }, Unit)
    }

    // FACTORY -------------------------------------------------------------------------------------
    class NewsListViewModelFactory(private val getPopularNews: GetPopularNews) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsListViewModel(getPopularNews) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}