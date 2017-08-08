package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.entity.MeneoUi
import es.mnmapp.aolv.meneame.ui.BaseFragment
import es.mnmapp.aolv.meneame.ui.view.common.ViewState
import es.mnmapp.aolv.meneame.ui.view.main.MainViewModel

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

class MainFragment : BaseFragment() {

    companion object Factory {
        fun newInstance() = MainFragment()
    }

    lateinit var swipeLayout : SwipeRefreshLayout
    lateinit var meneosList : RecyclerView
    lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState : Bundle?) {

        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity).get(MainViewModel::class.java)
        observeMeneos()
        observeViewState()
    }

    override fun onCreateView(inflater : LayoutInflater?,
                              container : ViewGroup?,
                              savedInstanceState : Bundle?) : View {

        val view = inflater!!.inflate(R.layout.fragment_main, container, false)

        swipeLayout = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        swipeLayout.setOnRefreshListener { mainViewModel.loadMeneos() }
        meneosList = view.findViewById<RecyclerView>(R.id.rv_list_meneos)
        // use a linear layout manager
        meneosList.layoutManager = LinearLayoutManager(this.context)

        mainViewModel.loadMeneos()
        return view
    }

    fun observeMeneos() {
        mainViewModel.meneos.observe(this, Observer<MutableList<MeneoUi>> {
            it?.let {
                if (meneosList.adapter == null) {
                    meneosList.adapter = MeneosAdapter(it)
                }
                else {
                    (meneosList.adapter as MeneosAdapter).updateList(it)
                }
            }
        })
    }

    fun observeViewState() {
        mainViewModel.state.observe(this, Observer<ViewState> {
            when (it) {
                ViewState.Refreshing -> swipeLayout.isRefreshing = true
                ViewState.Idle -> swipeLayout.isRefreshing = false
                else -> swipeLayout.isRefreshing = false
            }
        })
    }
}
