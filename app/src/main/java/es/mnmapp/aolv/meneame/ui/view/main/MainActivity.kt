package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.domain.usecase.GetPopularNews
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.view.main.fragment.NewsListFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            setFragment(NewsListFragment.newInstance())
        }
    }

    @Module
    class MainActivityModule {

        @Provides
        fun provideMainViewModelFactory(getPopularNews: GetPopularNews) = MainViewModelFactory(getPopularNews)
    }

    class MainViewModelFactory(private val getPopularNews: GetPopularNews) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST") return MainViewModel(getPopularNews) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
