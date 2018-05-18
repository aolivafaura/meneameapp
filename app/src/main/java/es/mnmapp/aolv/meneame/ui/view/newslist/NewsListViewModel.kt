package es.mnmapp.aolv.meneame.ui.view.newslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.usecase.GetPopularNews
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.entity.mapper.fromNewToNewUi

/**
 * Created by antonio on 2/24/18.
 */

class NewsListViewModel(private val getPopularNews: GetPopularNews) : ViewModel() {

    // Fields -----
    val news = MutableLiveData<List<NewUi>>()
    val state = MutableLiveData<ViewState>()

    // ViewModel overrides -----
    override fun onCleared() {
        super.onCleared()
        getPopularNews.clear()
    }

    // Class methods -----
    fun fetchNews() {
        val successHandler: ((List<New>) -> Unit) = {
            state.value = ViewState.Idle
            news.value = it.map { fromNewToNewUi(it) }
        }
        val errorHandler: ((Throwable) -> Unit) = {
            state.value = ViewState.Idle
        }

        state.value = ViewState.Refreshing
        getPopularNews.execute(Unit, successHandler, errorHandler)
    }

    // Inner classes -----
    enum class ViewState {
        Idle,
        Refreshing
    }
}