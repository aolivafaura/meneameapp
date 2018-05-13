package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.entity.NewUi
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

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val newsListViewModel by viewModel<NewsListViewModel>()

    private lateinit var listAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeNews()
        observeViewState()

        if (newsListViewModel.news.value == null) {
            newsListViewModel.fetchNews()
        }
    }

    override fun getFragmentLayout() = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun onRefreshAction() = { newsListViewModel.fetchNews() }

    private fun onListItemClick(newUi: NewUi) {
        mainViewModel.onNewSelected(newUi)
    }

    private fun initViews() {
        swiperefresh.setOnRefreshListener(onRefreshAction())

        rvListNews.layoutManager = LinearLayoutManager(this.context)
        listAdapter = NewsAdapter().apply {
            observeItemClick()
                    .subscribe({ onListItemClick(it) }, {})
                    .let { addDisposable(it) }
        }
        rvListNews.adapter = listAdapter
    }

    private fun observeNews() {
        newsListViewModel.news.observe(this, Observer<List<NewUi>> {
            it?.let { updateList(it) }
        })
    }

    private fun updateList(news: List<NewUi>) {
        listAdapter.updateList(news)
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

    companion object Factory {
        fun newInstance() = NewsListFragment()
    }
}
