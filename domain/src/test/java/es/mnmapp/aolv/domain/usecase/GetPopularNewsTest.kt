package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.repository.NewsRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.runners.MockitoJUnitRunner

/**
 * Created by antoniojoseoliva on 12/08/2017.
 */

@RunWith(MockitoJUnitRunner::class)
class GetPopularNewsTest {

    private lateinit var getPopularNews: GetPopularNews

    @Mock private lateinit var newsRepo: NewsRepo

    @Before
    fun setUp() {

        getPopularNews = GetPopularNews(newsRepo)
    }

    @Test
    fun Given_UseCaseCalled_When_ParamsAreValid_Then_MeneosAreRetrievedFromRepoOneTime() {
        getPopularNews.buildUseCaseObservable(Unit)

        verify(newsRepo, times(1)).getPopular()
    }
}