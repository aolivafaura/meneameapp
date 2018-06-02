package es.mnmapp.aolv.data.repository

import com.nhaarman.mockito_kotlin.mock
import es.mnmapp.aolv.data.repository.news.NewsDataRepo
import es.mnmapp.aolv.data.repository.news.cloud.NewsCloudRepo
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

/**
 * Created by antonio on 3/26/18.
 */
class NewsDataRepoTest {

    private val newsCloudRepo = mock<NewsCloudRepo>()
    private lateinit var newsDataRepo: NewsDataRepo

    @Before
    fun before(){
        newsDataRepo = NewsDataRepo(newsCloudRepo)
    }

    @Test
    fun `When popular news are requested, then popular news are retrieved from cloud`() {
        newsDataRepo.getPopular()
        verify(newsCloudRepo, times(1)).getPopular()
    }

    @Test
    fun `When top visited news are requested, then popular news are retrieved from cloud`() {
        newsDataRepo.getPopular()
        verify(newsCloudRepo, times(1)).getTopVisited()
    }
}