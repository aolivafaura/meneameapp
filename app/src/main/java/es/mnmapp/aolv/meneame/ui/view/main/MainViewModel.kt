package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.usecase.GetPopularNews
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.entity.mapper.fromNewToNewUi
import es.mnmapp.aolv.meneame.rx.BaseObserver
import es.mnmapp.aolv.meneame.ui.view.common.ViewState

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

class MainViewModel(private val getPopularNews: GetPopularNews) : ViewModel() {

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

    override fun onCleared() {
        getPopularNews.dispose()
    }
}
