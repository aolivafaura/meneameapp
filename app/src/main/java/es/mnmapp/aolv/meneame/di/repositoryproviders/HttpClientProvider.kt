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

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit


private const val NETWORK_READ_TIMEOUT = 20_000L // 20 seconds
private const val NETWORK_WRITE_TIMEOUT = 20_000L // 20 seconds
private const val NETWORK_CONNECT_TIMEOUT = 10_000L // 10 seconds

private const val CACHE_SIZE = 10L * 1024 * 1024 // 10 Mb

/**
 * Creates and provides http client
 *
 * @param[httpCacheDirectory] Directory to use as cache
 * @param[interceptors] Desired interceptors to add to http client
 *
 * @return built http client
 */
fun provideHttpClient(
        httpCacheDirectory: File,
        interceptors: ArrayList<Interceptor>
): OkHttpClient {
    val builder = OkHttpClient.Builder()
            .readTimeout(NETWORK_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(NETWORK_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(NETWORK_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .cache(Cache(httpCacheDirectory, CACHE_SIZE))

    for (interceptor in interceptors) {
        builder.addNetworkInterceptor(interceptor)
    }

    return builder.build()
}