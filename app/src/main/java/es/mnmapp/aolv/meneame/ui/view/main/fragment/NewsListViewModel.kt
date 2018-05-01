package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.arch.lifecycle.MutableLiveData
import es.mnmapp.aolv.domain.usecase.GetPopularNews
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.entity.mapper.fromNewToNewUi
import es.mnmapp.aolv.meneame.ui.BaseViewModel
import es.mnmapp.aolv.meneame.ui.view.common.ViewState

/**
 * Created by antonio on 2/24/18.
 */

class NewsListViewModel(private val getPopularNews: GetPopularNews) : BaseViewModel() {

    val news = MutableLiveData<MutableList<NewUi>>()
    val state = MutableLiveData<ViewState>()

    fun loadNews() {
        state.value = ViewState.Refreshing

        simpleIdlingResource.setIdleState(false)
        getPopularNews.execute(Unit,
                {
                    state.value = ViewState.Idle
                    news.value = it.map { fromNewToNewUi(it) }.toMutableList()

                    simpleIdlingResource.setIdleState(true)
                },
                {
                    state.value = ViewState.Idle

                    simpleIdlingResource.setIdleState(true)
                }
        )
    }

    override fun onCleared() {
        super.onCleared()

        getPopularNews.clear()
    }
}