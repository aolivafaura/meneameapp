package es.mnmapp.aolv.meneame.ui.view.newslist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.R.id.rvListNews
import es.mnmapp.aolv.meneame.R.id.swiperefresh
import es.mnmapp.aolv.meneame.connectivity.Connectivity
import es.mnmapp.aolv.meneame.entity.NewCellUi
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.BaseFragment
import es.mnmapp.aolv.meneame.ui.view.NavigationViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel


/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

class NewsListFragment : BaseFragment() {

    // Fields -----

    private val navigationViewModel by sharedViewModel<NavigationViewModel>()
    private val newsListViewModel by viewModel<NewsListViewModel>()

    // Variables -----

    private lateinit var listAdapter: NewsAdapter

    // Fragment overrides -----

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeNews()
        observeViewState()
        observeConnectivity()

        if (newsListViewModel.news.value == null) {
            newsListViewModel.fetchNews()
        }
    }

    private fun observeNews() {
        fun updateList(news: List<NewCellUi>) {
            listAdapter.updateList(news)
        }

        newsListViewModel.news.observe(this, Observer<List<NewCellUi>> {
            it?.let { updateList(it) }
        })
    }

    private fun observeViewState() {
        newsListViewModel.state.observe(this, Observer<NewsListViewModel.ViewState> {
            when (it) {
                NewsListViewModel.ViewState.Refreshing -> swiperefresh.isRefreshing = true
                NewsListViewModel.ViewState.Idle -> swiperefresh.isRefreshing = false
                else -> swiperefresh.isRefreshing = false
            }
        })
    }

    private fun observeConnectivity() {
        newsListViewModel.connectivityState.observe(this, Observer<Connectivity.State> {
            when (it) {
                Connectivity.State.Connected -> Toast.makeText(context, "CONNECTED", Toast.LENGTH_LONG).show()
                Connectivity.State.Disconnected -> Toast.makeText(context, "DISCONNECTED", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        setSwipeListener()
    }

    private fun initList() {
        val listener = { newCellUi: NewCellUi, _: View ->
            navigationViewModel.navigateToNewsDetail(activity as BaseActivity, newCellUi)
        }

        val linearLayoutManager = LinearLayoutManager(this.context)
        rvListNews.layoutManager = linearLayoutManager
        listAdapter = NewsAdapter().apply { onClickItem = listener }
        rvListNews.adapter = listAdapter
    }

    private fun setSwipeListener() {
        swiperefresh.setOnRefreshListener({ newsListViewModel.fetchNews() })
    }

    // BaseFragment overrides -----
    override fun getFragmentLayout() = R.layout.fragment_main

    override fun getAnalyticsName() = "NewsList"
}
