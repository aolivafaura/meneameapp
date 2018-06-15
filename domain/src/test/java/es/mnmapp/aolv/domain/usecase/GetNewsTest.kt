/*
 *     Copyright 2018 @ https://github.com/aolivafaura/
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

package es.mnmapp.aolv.domain.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.entity.Section
import es.mnmapp.aolv.domain.repository.DeviceRepository
import es.mnmapp.aolv.domain.repository.ImagesRepository
import es.mnmapp.aolv.domain.repository.NewsRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch

class GetNewsTest {

    private val scheduler = TestScheduler()

    private val newsRepo = mock<NewsRepository>()
    private val imagesRepo = mock<ImagesRepository>()
    private val deviceRepo = mock<DeviceRepository>()

    private lateinit var getNews: GetNews

    val signal = CountDownLatch(1)

    @Before
    fun before() {
        getNews = GetNews(scheduler, scheduler, newsRepo, imagesRepo, deviceRepo)
    }

    @Test
    fun `When popular news use case is built, then popular news are retrieved from repo`() {
        // Given
        val newsList = generateNews(2)
        whenever(newsRepo.getPopular()).thenReturn(Flowable.just(newsList))
        // When
        getNews.execute(Section.Popular, {}, {})
        // Then
        verify(newsRepo, times(1)).getPopular()
    }

    @Test
    fun `When top visited news use case is built, then top visited news are retrieved from repo`() {
        // Given
        val newsList = generateNews(2)
        whenever(newsRepo.getTopVisited()).thenReturn(Flowable.just(newsList))
        // When
        getNews.execute(Section.TopVisited, {}, {})
        // Then
        verify(newsRepo, times(1)).getTopVisited()
    }

    @Test
    fun `Given any use case, when news are retrieved, then source logos are retrieved`() {
        // Given
        val newsList = generateNews(2)
        whenever(newsRepo.getTopVisited()).thenReturn(Flowable.just(newsList))
        // When
        getNews.execute(Section.TopVisited, {}, {})
        scheduler.triggerActions()
        // Then
        verify(imagesRepo, times(1)).getLogosForSources(*newsList.map { it.from }.toTypedArray())
    }

    @Test
    fun `Given news with some blank placeholder, when logos call success, then placeholders are retrieved`() {
        // Given
        val newsList = generateNews(2, true)
        whenever(newsRepo.getTopVisited()).thenReturn(Flowable.just(newsList))
        // When
        whenever(imagesRepo.getLogosForSources(any())).thenReturn(Single.just(emptyMap()))
        getNews.execute(Section.TopVisited, {}, {})
        scheduler.triggerActions()
        // Then
        verify(imagesRepo, times(1)).getPlaceholders(anyOrNull())
    }

    @Test
    fun `Given news with some blank placeholder, when logos call fails, then placeholders are retrieved`() {
        // Given
        val newsList = generateNews(2, true)
        whenever(newsRepo.getTopVisited()).thenReturn(Flowable.just(newsList))
        // When
        whenever(imagesRepo.getLogosForSources(any())).thenReturn(Single.error(Throwable("")))
        getNews.execute(Section.TopVisited, {}, {})
        scheduler.triggerActions()
        // Then
        verify(imagesRepo, times(1)).getPlaceholders(anyOrNull())
    }

    @Test
    fun `Given news without blank placeholders, when logos call success, then no placeholders are retrieved`() {
        // Given
        val newsList = generateNews(2)
        whenever(newsRepo.getTopVisited()).thenReturn(Flowable.just(newsList))
        // When
        whenever(imagesRepo.getLogosForSources(any())).thenReturn(Single.just(emptyMap()))
        getNews.execute(Section.TopVisited, {}, {})
        scheduler.triggerActions()
        // Then
        verify(imagesRepo, times(0)).getPlaceholders(anyOrNull())
    }

    @Test
    fun `Given news retrieved, when logos and placeholder calls fails, then emitted list is the original one`() {
        // Given
        val newsList = generateNews(2)
        whenever(newsRepo.getTopVisited()).thenReturn(Flowable.just(newsList))
        // When
        whenever(imagesRepo.getLogosForSources(any())).thenReturn(Single.error(Throwable("")))
        whenever(imagesRepo.getPlaceholders(any())).thenReturn(Single.error(Throwable("")))
        // Then
        var receivedList: List<New>? = null
        getNews.execute(
            Section.TopVisited,
            {
                receivedList = it
                signal.countDown()
            },
            {
            }
        )
        scheduler.triggerActions()
        signal.await()
        assertEquals(newsList, receivedList)
    }

    @Test
    fun `Given news retrieved, when logos call success, then logos are added to list`() {
        // Given
        val newsList = generateNews(2)
        val logos = generateLogos(2)
        whenever(newsRepo.getTopVisited()).thenReturn(Flowable.just(newsList))
        // When
        whenever(imagesRepo.getLogosForSources(any())).thenReturn(Single.just(logos))
        whenever(imagesRepo.getPlaceholders(any())).thenReturn(Single.just(emptyList()))
        // Then
        val expectedList = mutableListOf<New>().apply {
            newsList.forEach {
                add(it.copy(logoUrl = logos[it.from] ?: ""))
            }
        }
        var receivedList: List<New>? = null
        getNews.execute(
            Section.TopVisited,
            {
                receivedList = it
                signal.countDown()
            },
            {
            }
        )
        scheduler.triggerActions()
        signal.await()
        assertEquals(expectedList, receivedList)
    }

    private fun generateNews(count: Int, withBlankThumb: Boolean = false): List<New> {
        val list = mutableListOf<New>()
        for (i in 0..count) {
            list.add(
                New(
                    i.toLong(),
                    "url$i",
                    "title$i",
                    "category$i",
                    if (withBlankThumb) "" else "thumb$i",
                    "from$i",
                    i,
                    i,
                    i,
                    i,
                    i.toLong(),
                    i.toLong(),
                    "tags$i",
                    ""
                )
            )
        }

        return list.toMutableList()
    }

    private fun generateLogos(count: Int): Map<String, String> {
        val logosMap = mutableMapOf<String, String>()
        for (i in 0..count) {
            logosMap["from$i"] = "logo$i"
        }

        return logosMap
    }
}
