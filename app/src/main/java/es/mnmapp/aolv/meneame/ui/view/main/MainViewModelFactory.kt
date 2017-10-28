package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import es.mnmapp.aolv.domain.usecase.GetPopularMeneos

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

class MainViewModelFactory(private val getPopularMeneos: GetPopularMeneos) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MainViewModel(getPopularMeneos) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
