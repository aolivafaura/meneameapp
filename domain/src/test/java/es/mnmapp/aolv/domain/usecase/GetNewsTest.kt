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
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

class GetNewsTest {

    private val scheduler = TestScheduler()

    private val newsRepo = mock<NewsRepository>()
    private val imagesRepo = mock<ImagesRepository>()
    private val deviceRepo = mock<DeviceRepository>()

    private val newsList = generateNews(2)
    private lateinit var getNews: GetNews

    @Before
    fun before() {
        getNews = GetNews(scheduler, scheduler, newsRepo, imagesRepo, deviceRepo)
    }

    @Test
    fun `When popular news use case is built, then popular news are retrieved from repo`() {
        whenever(newsRepo.getPopular()).thenReturn(Flowable.just(newsList))

        getNews.execute(Section.Popular, {}, {})
        Thread.sleep(2000)
        verify(newsRepo, times(1)).getPopular()
    }

    private fun generateNews(count: Int): List<New> {
        val list = mutableListOf<New>()
        for (i in 0..count) {
            list.add(
                New(
                    i.toLong(),
                    "url$i",
                    "title$i",
                    "category$i",
                    "thumb$i",
                    "from$i",
                    i,
                    i,
                    i,
                    i,
                    i.toLong(),
                    i.toLong(),
                    "tags$i",
                    "logoUrl$i"
                )
            )
        }

        return list.toMutableList()
    }
}
