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
import es.mnmapp.aolv.meneame.rx.BaseObserver
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.BaseFragment
import es.mnmapp.aolv.meneame.ui.view.common.ViewState
import es.mnmapp.aolv.meneame.ui.view.main.MainViewModel
import es.mnmapp.aolv.meneame.ui.view.webview.WebViewActivity

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

class MainFragment : BaseFragment() {

    //region Companion object
    companion object Factory {
        fun newInstance() = MainFragment()
    }
    //endregion

    //region Variables
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var meneosList: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    //endregion

    //region Fragment overrides
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity).get(MainViewModel::class.java)

        observeMeneos()
        observeViewState()
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_main, container, false)

        initViews(view)

        mainViewModel.loadMeneos()
        return view
    }
    //endregion

    //region View actions
    private fun onRefreshAction() = { mainViewModel.loadMeneos() }

    private fun onListItemClick(meneoUi: MeneoUi) {
        startActivity(WebViewActivity.createIntent(activity as BaseActivity, meneoUi.url!!, meneoUi.title!!))
    }
    //endregion

    private fun initViews(view: View) {
        swipeLayout = view.findViewById(R.id.swiperefresh)
        swipeLayout.setOnRefreshListener(onRefreshAction())

        meneosList = view.findViewById(R.id.rv_list_meneos)
        meneosList.layoutManager = LinearLayoutManager(this.context)
    }

    private fun observeMeneos() {
        mainViewModel.meneos.observe(this, Observer<MutableList<MeneoUi>> {
            it?.let {
                if (meneosList.adapter == null) {
                    initAdapter(it)
                } else {
                    (meneosList.adapter as MeneosAdapter).updateList(it)
                }
            }
        })
    }

    private fun observeViewState() {
        mainViewModel.state.observe(this, Observer<ViewState> {
            when (it) {
                ViewState.Refreshing -> swipeLayout.isRefreshing = true
                ViewState.Idle -> swipeLayout.isRefreshing = false
                else -> swipeLayout.isRefreshing = false
            }
        })
    }

    private fun initAdapter(items: MutableList<MeneoUi>) {
        meneosList.adapter = MeneosAdapter(items)
        (meneosList.adapter as MeneosAdapter)
                .observeItemClick()
                .subscribe(object : BaseObserver<MeneoUi>() {
                    override fun onNext(result: MeneoUi) {
                        onListItemClick(result)
                        logger.ev("ITEM_CLICK", null)
                    }
                })
    }
}
