package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.ui.BaseFragment

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

class MainFragment : BaseFragment() {

    companion object Factory {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater : LayoutInflater?,
                              container : ViewGroup?,
                              savedInstanceState : Bundle?) : View {

        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }
}
