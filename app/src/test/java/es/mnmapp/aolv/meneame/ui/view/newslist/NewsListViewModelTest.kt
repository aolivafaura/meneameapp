package es.mnmapp.aolv.meneame.ui.view.newslist

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.usecase.GetNews
import es.mnmapp.aolv.meneame.entity.fromNewToNewCellUi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


/**
 * Created by antonio on 3/25/18.
 */
class NewsListViewModelTest {

    // Bypass main thread on MutableLiveData objects
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    var getPopularNews = mock<GetNews>()
    lateinit var newsListViewModel: NewsListViewModel

    private val newsList = listOf(New(1L, "url", "title", "thumb"))

    @Before
    fun before() {
        newsListViewModel = NewsListViewModel(getPopularNews)
    }

    @Test
    fun `Given user on news list screen, when news are fetched, then popular news use case is called`() {
        newsListViewModel.fetchNews()
        verify(getPopularNews, times(1)).execute(any(), any(), any(), any())
    }

    @Test
    fun `Given news being fetched, when response is being waited, then view state is refreshing`() {
        newsListViewModel.fetchNews()
        assertEquals(NewsListViewModel.ViewState.Refreshing, newsListViewModel.state.value)
    }

    @Test
    fun `Given news being fetched, when successful response, then view state is idle`() {
        val successCaptor = argumentCaptor<(List<New>) -> Unit>()

        newsListViewModel.fetchNews()
        verify(getPopularNews, times(1)).execute(any(), successCaptor.capture(), any(), any())
        successCaptor.firstValue.invoke(emptyList())
        assertEquals(NewsListViewModel.ViewState.Idle, newsListViewModel.state.value)
    }

    @Test
    fun `Given news being fetched, when successful response, then news list is mapped`() {
        val successCaptor = argumentCaptor<(List<New>) -> Unit>()

        newsListViewModel.fetchNews()
        verify(getPopularNews, times(1)).execute(any(), successCaptor.capture(), any(), any())
        successCaptor.firstValue.invoke(newsList)
        assertEquals(newsList.map { fromNewToNewCellUi(it) }, newsListViewModel.news.value)
    }

    @Test
    fun `Given news being fetched, when error response, then view state is idle`() {
        val errorCaptor = argumentCaptor<(Throwable) -> Unit>()

        newsListViewModel.fetchNews()
        verify(getPopularNews, times(1)).execute(any(), any(), errorCaptor.capture(), any())
        errorCaptor.firstValue.invoke(Throwable())
        assertEquals(NewsListViewModel.ViewState.Idle, newsListViewModel.state.value)
    }
}
