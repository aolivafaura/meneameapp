package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.rx.BaseObserver
import es.mnmapp.aolv.meneame.ui.BaseFragment
import es.mnmapp.aolv.meneame.ui.view.common.ViewState
import es.mnmapp.aolv.meneame.ui.view.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel


/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

class NewsListFragment : BaseFragment() {

    val mainViewModel: MainViewModel by sharedViewModel()
    val newsListViewModel: NewsListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeNews()
        observeViewState()
    }

    override fun getFragmentLayout() = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        rvListNews.scrollState

        if (savedInstanceState?.containsKey(BUNDLE_KEY_ITEMS) == true) {
            if (rvListNews.adapter == null) {
                initAdapter(savedInstanceState.getParcelableArrayList(BUNDLE_KEY_ITEMS))
            } else {
                (rvListNews.adapter as NewsAdapter).updateList(savedInstanceState.getParcelableArrayList(BUNDLE_KEY_ITEMS))
            }
        } else {
            newsListViewModel.loadNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(BUNDLE_KEY_ITEMS, ArrayList(newsListViewModel.news.value))
    }

    private fun onRefreshAction() = { newsListViewModel.loadNews() }

    private fun onListItemClick(newUi: NewUi) {
        mainViewModel.onNewSelected(newUi)
    }

    private fun initViews() {
        swiperefresh.setOnRefreshListener(onRefreshAction())

        rvListNews.layoutManager = LinearLayoutManager(this.context)

        fabMenu.clickCallback = { handleFabClick(it) }
    }

    private fun observeNews() {
        newsListViewModel.news.observe(this, Observer<MutableList<NewUi>> {
            it?.let {
                if (rvListNews.adapter == null) {
                    initAdapter(it)
                } else {
                    (rvListNews.adapter as NewsAdapter).updateList(it)
                }
            }
        })
    }

    private fun observeViewState() {
        newsListViewModel.state.observe(this, Observer<ViewState> {
            when (it) {
                ViewState.Refreshing -> swiperefresh.isRefreshing = true
                ViewState.Idle -> swiperefresh.isRefreshing = false
                else -> swiperefresh.isRefreshing = false
            }
        })
    }

    private fun handleFabClick(id: Int) {
        //logger.d(id.toString())
    }

    private fun initAdapter(items: MutableList<NewUi>) {
        rvListNews.adapter = NewsAdapter(items)
        (rvListNews.adapter as NewsAdapter)
                .observeItemClick()
                .subscribe(object : BaseObserver<NewUi>() {
                    override fun onNext(result: NewUi) {
                        onListItemClick(result)
                    }
                })
    }

    companion object Factory {
        private const val BUNDLE_KEY_ITEMS = "MainFragmentItemsKey"
        fun newInstance() = NewsListFragment()
    }
}
