package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import es.mnmapp.aolv.domain.entity.Meneo
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.ui.BaseFragment
import es.mnmapp.aolv.meneame.ui.view.main.MainViewModel
import timber.log.Timber

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

        observeMeneos()
    }

    override fun onCreateView(inflater : LayoutInflater?,
                              container : ViewGroup?,
                              savedInstanceState : Bundle?) : View {

        val v = inflater!!.inflate(R.layout.fragment_main, container, false)
        v.findViewById<Button>(R.id.b_refresh).setOnClickListener { mainViewModel.loadMeneos() }
        return v
    }

    fun observeMeneos() {

        mainViewModel.meneos.observe(this, Observer<List<Meneo>> {

            Timber.d(it.toString())
        })
    }
}
