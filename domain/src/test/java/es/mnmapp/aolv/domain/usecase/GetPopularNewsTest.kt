package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.repository.NewsRepo
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

/**
 * Created by antoniojoseoliva on 12/08/2017.
 */

class GetPopularNewsTest {

    private lateinit var getPopularNews: GetPopularNews

    @Mock
    private lateinit var newsRepo: NewsRepo

    private val scheduler = TestScheduler()

    @Before
    fun setUp() {
        initMocks(this)
        getPopularNews = GetPopularNews(scheduler, scheduler, newsRepo)
    }

    @Test
    fun `Given use case called, when params are valid, then news are retrieved from repository once`() {
        getPopularNews.buildUseCaseObservable(Unit)
        verify(newsRepo, times(1)).getPopular()
    }
}