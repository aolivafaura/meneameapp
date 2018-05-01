package es.mnmapp.aolv.meneame.ui

import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.meneame.testing.SimpleIdlingResource

open  class BaseViewModel: ViewModel() {

    lateinit var simpleIdlingResource: SimpleIdlingResource
}