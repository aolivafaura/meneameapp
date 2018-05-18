package es.mnmapp.aolv.meneame.ui.view.newslist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.BaseFragment
import es.mnmapp.aolv.meneame.ui.view.NavigationViewModel
import es.mnmapp.aolv.meneame.ui.view.common.ViewState
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel


/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

class NewsListFragment : BaseFragment() {

    private val navigationViewModel by sharedViewModel<NavigationViewModel>()
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

    override fun getAnalyticsName() = "NewsList"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        setSwipeListener()
    }

    private fun onRefreshAction() = { newsListViewModel.fetchNews() }

    private fun initList() {
        val listener = { newUi: NewUi, _: View ->
            navigationViewModel.navigateToNewsDetail(activity as BaseActivity, newUi)
        }

        rvListNews.layoutManager = LinearLayoutManager(this.context)
        listAdapter = NewsAdapter().apply { onClickItem = listener }
        rvListNews.adapter = listAdapter
    }

    private fun setSwipeListener() {
        swiperefresh.setOnRefreshListener(onRefreshAction())
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
}
