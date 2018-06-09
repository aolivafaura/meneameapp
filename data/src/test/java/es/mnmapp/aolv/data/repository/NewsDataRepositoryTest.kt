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

package es.mnmapp.aolv.data.repository

import com.nhaarman.mockito_kotlin.mock
import es.mnmapp.aolv.data.repository.news.NewsDataRepository
import es.mnmapp.aolv.data.repository.news.datasource.cloud.NewsCloudDataSource
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

/**
 * Test for news data repository
 *
 * @see NewsDataRepository
 */
class NewsDataRepositoryTest {

    private val newsCloudRepo = mock<NewsCloudDataSource>()
    private lateinit var newsDataRepository: NewsDataRepository

    @Before
    fun before() {
        newsDataRepository = NewsDataRepository(newsCloudRepo)
    }

    @Test
    fun `When popular news are requested, then popular news are retrieved from cloud`() {
        newsDataRepository.getPopular()
        verify(newsCloudRepo, times(1)).getPopular()
    }

    @Test
    fun `When top visited news are requested, then popular news are retrieved from cloud`() {
        newsDataRepository.getTopVisited()
        verify(newsCloudRepo, times(1)).getTopVisited()
    }
}