package es.mnmapp.aolv.data.repository

import com.nhaarman.mockito_kotlin.mock
import es.mnmapp.aolv.data.repository.news.NewsDataRepository
import es.mnmapp.aolv.data.repository.news.datasource.cloud.NewsCloudDataSource
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

/**
 * Created by antonio on 3/26/18.
 */
class NewsDataRepositoryTest {

    private val newsCloudRepo = mock<NewsCloudDataSource>()
    private lateinit var newsDataRepository: NewsDataRepository

    @Before
    fun before(){
        newsDataRepository = NewsDataRepository(newsCloudRepo)
    }

    @Test
    fun `When popular news are requested, then popular news are retrieved from cloud`() {
        newsDataRepository.getPopular()
        verify(newsCloudRepo, times(1)).getPopular()
    }

    @Test
    fun `When top visited news are requested, then popular news are retrieved from cloud`() {
        newsDataRepository.getPopular()
        verify(newsCloudRepo, times(1)).getTopVisited()
    }
}