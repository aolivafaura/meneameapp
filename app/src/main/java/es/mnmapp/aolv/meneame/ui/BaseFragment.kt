package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.mnmapp.aolv.meneame.testing.SimpleIdlingResource
import org.koin.android.ext.android.inject

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

abstract class BaseFragment : Fragment() {

    @VisibleForTesting
    private val simpleIdlingResource: SimpleIdlingResource by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        for (viewModel in getViewModels()) {
            viewModel.simpleIdlingResource = simpleIdlingResource
        }
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    @LayoutRes
    abstract fun getFragmentLayout(): Int

    abstract fun getViewModels(): List<BaseViewModel>
}
