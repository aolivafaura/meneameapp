package es.mnmapp.aolv.domain.usecase

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.repository.NewsRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

class GetPopularNewsTest {

    private val scheduler = TestScheduler()
    private val newsRepo = mock<NewsRepository>()

    private val newsList = listOf(
            New(1L, "url", "title", "thumb"),
            New(2L, "url2", "title2", "thumb2")
    )
    private lateinit var getPopularNews: GetPopularNews

    @Before
    fun before() {
        getPopularNews = GetPopularNews(scheduler, scheduler, newsRepo)
    }

    @Test
    fun `When popular news use case is built, then popular news are retrieved from repo`() {
        whenever(newsRepo.getPopular()).thenReturn(Flowable.just(newsList))

        getPopularNews.execute(Unit, {}, {})
        verify(newsRepo, times(1)).getPopular()
    }
}