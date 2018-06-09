/*
 *     Copyright 2018 @ https://github.com/aolivafaura
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.mnmapp.aolv.meneame.ui.view.newslist

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.usecase.GetNews
import es.mnmapp.aolv.meneame.connectivity.Connectivity
import es.mnmapp.aolv.meneame.entity.fromNewToNewCellUi
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * News list view model tests
 *
 * @see NewsListViewModel
 */
class NewsListViewModelTest {

    // Bypass main thread on MutableLiveData objects
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private var getPopularNews = mock<GetNews>()
    private var connectivity = mock<Connectivity>()

    private lateinit var newsListViewModel: NewsListViewModel

    private val newsList = generateNews(1)

    @Before
    fun before() {
        whenever(connectivity.observeConnectivity()).thenReturn(Flowable.empty())
        newsListViewModel = NewsListViewModel(getPopularNews, connectivity)
    }

    @Test
    fun `Given user on news list screen, when news are fetched, then popular news use case is called`() {
        newsListViewModel.fetchNews()
        verify(getPopularNews, times(1)).execute(any(), any(), any())
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
        verify(getPopularNews, times(1)).execute(any(), successCaptor.capture(), any())
        successCaptor.firstValue.invoke(emptyList())
        assertEquals(NewsListViewModel.ViewState.Idle, newsListViewModel.state.value)
    }

    @Test
    fun `Given news being fetched, when successful response, then news list is mapped`() {
        val successCaptor = argumentCaptor<(List<New>) -> Unit>()

        newsListViewModel.fetchNews()
        verify(getPopularNews, times(1)).execute(any(), successCaptor.capture(), any())
        successCaptor.firstValue.invoke(newsList)
        assertEquals(newsList.map { fromNewToNewCellUi(it) }, newsListViewModel.news.value)
    }

    @Test
    fun `Given news being fetched, when error response, then view state is idle`() {
        val errorCaptor = argumentCaptor<(Throwable) -> Unit>()

        newsListViewModel.fetchNews()
        verify(getPopularNews, times(1)).execute(any(), any(), errorCaptor.capture())
        errorCaptor.firstValue.invoke(Throwable())
        assertEquals(NewsListViewModel.ViewState.Idle, newsListViewModel.state.value)
    }

    private fun generateNews(count: Int): List<New> {
        val list = mutableListOf<New>()
        for (i in 0..count) {
            list.add(
                New(
                    i.toLong(),
                    "url",
                    "title",
                    "category",
                    "thumb",
                    "from",
                    1,
                    1,
                    1,
                    1,
                    1000L,
                    1000L,
                    "tags",
                    "logoUrl"
                )
            )
        }

        return list.toMutableList()
    }
}
