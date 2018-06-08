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

package es.mnmapp.aolv.meneame.di.repositoryproviders

import es.mnmapp.aolv.data.repository.news.datasource.cloud.MeneameService
import okhttp3.OkHttpClient

/**
 * Creates and provides meneame cloud service
 *
 * @param[okHttpClient] http client
 * @param[baseUrl] base url for cloud service
 *
 * @return meneame cloud service
 */
fun provideMeneameService(okHttpClient: OkHttpClient, baseUrl: String): MeneameService =
        MeneameService.create(okHttpClient, baseUrl)
