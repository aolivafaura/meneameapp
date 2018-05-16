package es.mnmapp.aolv.meneame.ui.view.newslist.fragment

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import es.mnmapp.aolv.domain.usecase.GetPopularNews
import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by antonio on 3/25/18.
 */
@RunWith(MockitoJUnitRunner::class)
class NewsListViewModelTest {

    // Bypass main thread on MutableLiveData objects
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularNews: GetPopularNews

    lateinit var newsListViewModel: NewsListViewModel

    @Before
    fun before() {
        newsListViewModel = NewsListViewModel(getPopularNews)
        newsListViewModel.simpleIdlingResource = mock()
    }

    @Test
    fun `When news are loaded for first time, then popular news are retrieved`() {
        newsListViewModel.fetchNews()

        verify(getPopularNews, times(1)).execute(any(), any(), any(), any())
    }
}