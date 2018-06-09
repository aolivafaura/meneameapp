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

package es.mnmapp.aolv.meneame.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import es.mnmapp.aolv.meneame.connectivity.Connectivity
import es.mnmapp.aolv.meneame.interceptors.LocalCacheInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Provides network interceptor for dev compilation.
 *
 * @param[connectivity] Connectivity
 *
 * @return list of interceptors
 */
fun provideNetworkInterceptors(connectivity: Connectivity): ArrayList<Interceptor> =
    arrayListOf(
        getLocalCacheInterceptor(connectivity),
        getStethoInterceptor(),
        getLoggingInterceptor()
    )

const val CACHE_MAX_AGE = 5 // 5 seconds
const val CACHE_MAX_STALE = 30 // 30 seconds

private fun getLocalCacheInterceptor(connectivity: Connectivity): LocalCacheInterceptor =
    LocalCacheInterceptor(connectivity, CACHE_MAX_AGE, CACHE_MAX_STALE)

private fun getStethoInterceptor(): StethoInterceptor = StethoInterceptor()

private fun getLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
