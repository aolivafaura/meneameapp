package es.mnmapp.aolv.meneame.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import es.mnmapp.aolv.meneame.ui.BaseFragment

inline fun <reified T : ViewModel> BaseFragment.viewModel(): Lazy<T> =
    lazy { ViewModelProviders.of(this, this.viewModelFactory)[T::class.java] }

inline fun <reified T : ViewModel> BaseFragment.sharedViewModel(): Lazy<T> =
    lazy { ViewModelProviders.of(activity!!).get(T::class.java) }
