package es.mnmapp.aolv.meneame.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import es.mnmapp.aolv.meneame.utils.Lg
import javax.inject.Inject

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

abstract class BaseFragment : Fragment() {

    @Inject protected lateinit var logger : Lg

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(getFragmentLayout(), container, false)
    }

    @LayoutRes
    abstract fun getFragmentLayout(): Int
}
