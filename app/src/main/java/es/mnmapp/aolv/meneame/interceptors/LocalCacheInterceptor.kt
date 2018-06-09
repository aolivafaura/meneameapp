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

package es.mnmapp.aolv.meneame.interceptors

import es.mnmapp.aolv.meneame.connectivity.Connectivity
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Interceptor to cache network responses
 *
 * @param[connectivity]
 * @param[cacheMaxAge] Max **seconds** allowed for cached responses when device has connectivity.
 * @param[cacheMaxStale] Max **seconds** allowed for cached responses when device hasn't connectivity.
 */
class LocalCacheInterceptor(
    private val connectivity: Connectivity,
    private val cacheMaxAge: Int,
    private val cacheMaxStale: Int
) : Interceptor {

    private val cacheControlMaxAge = 0
    private val cacheControlMaxStale = 0

    override fun intercept(chain: Interceptor.Chain?): Response {
        val cacheBuilder = CacheControl.Builder().apply {
            maxAge(cacheControlMaxAge, TimeUnit.MILLISECONDS)
            maxStale(cacheControlMaxStale, TimeUnit.DAYS)
        }

        val cacheControl = cacheBuilder.build()
        var request = chain!!.request()

        val networkAvailable = connectivity.isConnected()
        if (networkAvailable) {
            request = request.newBuilder().cacheControl(cacheControl).build()
        }

        val response = chain.proceed(request)
        return if (networkAvailable) {
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$cacheMaxAge")
                .build()
        } else {
            response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$cacheMaxStale")
                .build()
        }
    }
}