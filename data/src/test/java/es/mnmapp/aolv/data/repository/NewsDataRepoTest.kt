package es.mnmapp.aolv.data.repository

import es.mnmapp.aolv.data.repository.cloud.NewsCloudRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by antonio on 3/26/18.
 */
@RunWith(MockitoJUnitRunner::class)
class NewsDataRepoTest {

    @Mock
    lateinit var newsCloudRepo: NewsCloudRepo

    lateinit var newsDataRepo: NewsDataRepo

    @Before
    fun before(){
        newsDataRepo = NewsDataRepo(newsCloudRepo)
    }

    @Test
    fun `When popular news are requested, then popular news are retrieved from cloud`() {
        newsDataRepo.getPopular()

        verify(newsCloudRepo, times(1)).getPopular()
    }
}