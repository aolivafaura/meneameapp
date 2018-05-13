package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.meneame.entity.NewUi

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

class MainViewModel : ViewModel() {

    val selectedNew = MutableLiveData<NewUi>()

    fun onNewSelected(selectedNew: NewUi) {
        this.selectedNew.value = selectedNew
    }

    fun clearSelectedNew() {
        selectedNew.value = null
    }
}
