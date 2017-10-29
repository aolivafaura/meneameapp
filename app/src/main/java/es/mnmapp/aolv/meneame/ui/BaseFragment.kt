package es.mnmapp.aolv.meneame.ui

import android.content.Context
import android.support.v4.app.Fragment
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
}
