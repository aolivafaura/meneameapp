package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.domain.entity.Meneo
import es.mnmapp.aolv.domain.usecase.GetPopularMeneos
import es.mnmapp.aolv.meneame.entity.MeneoUi
import es.mnmapp.aolv.meneame.entity.mapper.fromMeneoToMeneoUi
import es.mnmapp.aolv.meneame.rx.BaseObserver
import es.mnmapp.aolv.meneame.ui.view.common.ViewState

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

class MainViewModel(private val getPopularMeneos: GetPopularMeneos) : ViewModel() {

    val meneos = MutableLiveData<MutableList<MeneoUi>>()
    val state = MutableLiveData<ViewState>()

    fun loadMeneos() {

        state.value = ViewState.Refreshing

        getPopularMeneos.execute(object : BaseObserver<List<Meneo>>() {

            override fun onError(error: Throwable) {
                super.onError(error)
                state.value = ViewState.Idle
            }

            override fun onNext(result: List<Meneo>) {
                state.value = ViewState.Idle
                meneos.value = result.map { fromMeneoToMeneoUi(it) }.toMutableList()
            }
        }, Unit)
    }

    override fun onCleared() {
        getPopularMeneos.dispose()
    }
}
