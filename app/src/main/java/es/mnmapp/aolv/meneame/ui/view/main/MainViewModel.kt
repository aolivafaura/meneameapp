package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.domain.entity.Meneo
import es.mnmapp.aolv.domain.usecase.GetPopularMeneos
import io.reactivex.observers.DisposableObserver
import timber.log.Timber

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

class MainViewModel(val getPopularMeneos : GetPopularMeneos) : ViewModel() {

    val meneos = MutableLiveData<List<Meneo>>()

    fun loadMeneos() {

        getPopularMeneos.execute(object : DisposableObserver<List<Meneo>>() {
            override fun onComplete() {
            }

            override fun onError(e : Throwable) {
                Timber.w(e.message)
            }

            override fun onNext(t : List<Meneo>) {
                meneos.value = t
            }

        }, Unit)
    }

    override fun onCleared() {

    }

    init { loadMeneos() }
}
