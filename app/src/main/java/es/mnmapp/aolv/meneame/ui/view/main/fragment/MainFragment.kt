package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.ui.BaseFragment
import es.mnmapp.aolv.meneame.ui.view.main.MainViewModel

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

class MainFragment : BaseFragment() {

    companion object Factory {
        fun newInstance() = MainFragment()
    }

    lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater : LayoutInflater?,
                              container : ViewGroup?,
                              savedInstanceState : Bundle?) : View {

        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }
}
