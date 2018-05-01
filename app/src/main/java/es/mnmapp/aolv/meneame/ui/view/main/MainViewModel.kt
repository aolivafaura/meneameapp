package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.MutableLiveData
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.ui.BaseViewModel

/**
 * Created by antoniojoseoliva on 25/07/2017.
 */

class MainViewModel : BaseViewModel() {

    val selectedNew = MutableLiveData<NewUi>()

    fun onNewSelected(selectedNew: NewUi) {
        this.selectedNew.value = selectedNew
    }
}
