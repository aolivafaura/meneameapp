package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

class MainFragment : BaseFragment() {

    private val BUNDLE_KEY_ITEMS = "MainFragmentItemsKey"

    //region Companion object
    companion object Factory {
        fun newInstance() = MainFragment()
    }
    //endregion

    //region Variables
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

        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        if (savedInstanceState?.containsKey(BUNDLE_KEY_ITEMS) == true) {
            if (rvListMeneos.adapter == null) {
                initAdapter(savedInstanceState.getParcelableArrayList(BUNDLE_KEY_ITEMS))
            } else {
                (rvListMeneos.adapter as MeneosAdapter).updateList(savedInstanceState.getParcelableArrayList(BUNDLE_KEY_ITEMS))
            }
        } else {
            mainViewModel.loadMeneos()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putParcelableArrayList(BUNDLE_KEY_ITEMS, ArrayList(mainViewModel.meneos.value))
    }
    //endregion

    //region View actions
    private fun onRefreshAction() = { mainViewModel.loadMeneos() }

    private fun onListItemClick(meneoUi: MeneoUi) {
        startActivity(WebViewActivity.createIntent(activity as BaseActivity, meneoUi.url!!, meneoUi.title!!))
    }
    //endregion

    private fun initViews() {
        swiperefresh.setOnRefreshListener(onRefreshAction())

        rvListMeneos.layoutManager = LinearLayoutManager(this.context)
    }

    private fun observeMeneos() {
        mainViewModel.meneos.observe(this, Observer<MutableList<MeneoUi>> {
            it?.let {
                if (rvListMeneos.adapter == null) {
                    initAdapter(it)
                } else {
                    (rvListMeneos.adapter as MeneosAdapter).updateList(it)
                }
            }
        })
    }

    private fun observeViewState() {
        mainViewModel.state.observe(this, Observer<ViewState> {
            when (it) {
                ViewState.Refreshing -> swiperefresh.isRefreshing = true
                ViewState.Idle -> swiperefresh.isRefreshing = false
                else -> swiperefresh.isRefreshing = false
            }
        })
    }

    private fun initAdapter(items: MutableList<MeneoUi>) {
        rvListMeneos.adapter = MeneosAdapter(items)
        (rvListMeneos.adapter as MeneosAdapter)
                .observeItemClick()
                .subscribe(object : BaseObserver<MeneoUi>() {
                    override fun onNext(result: MeneoUi) {
                        onListItemClick(result)
                        logger.ev("ITEM_CLICK", null)
                    }
                })
    }
}
