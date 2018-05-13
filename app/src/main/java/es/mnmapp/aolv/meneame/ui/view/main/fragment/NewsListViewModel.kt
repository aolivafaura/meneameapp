package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.domain.usecase.GetPopularNews
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.entity.mapper.fromNewToNewUi
import es.mnmapp.aolv.meneame.ui.view.common.ViewState

/**
 * Created by antonio on 2/24/18.
 */

class NewsListViewModel(private val getPopularNews: GetPopularNews) : ViewModel() {

    val news = MutableLiveData<MutableList<NewUi>>()
    val state = MutableLiveData<ViewState>()

    fun loadNews() {
        state.value = ViewState.Refreshing
        getPopularNews.execute(Unit,
                {
                    state.value = ViewState.Idle
                    news.value = it.map { fromNewToNewUi(it) }.toMutableList()
                },
                {
                    state.value = ViewState.Idle
                }
        )
    }

    override fun onCleared() {
        super.onCleared()

        getPopularNews.clear()
    }
}