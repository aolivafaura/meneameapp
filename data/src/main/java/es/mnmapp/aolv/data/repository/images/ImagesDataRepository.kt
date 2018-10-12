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

package es.mnmapp.aolv.data.repository.images

import es.mnmapp.aolv.data.EndpointUrls
import es.mnmapp.aolv.data.entity.PlaceholderEntity
import es.mnmapp.aolv.data.entity.mapToPlaceholder
import es.mnmapp.aolv.data.repository.images.datasource.cloud.PlaceholdersCloudDataSource
import es.mnmapp.aolv.data.repository.images.datasource.local.PlaceholdersLocalDataSource
import es.mnmapp.aolv.domain.entity.Placeholder
import es.mnmapp.aolv.domain.repository.ImagesRepository
import es.mnmapp.aolv.domain.repository.ScreenDensity
import io.reactivex.Single
import io.reactivex.internal.operators.single.SingleJust
import javax.inject.Inject

class ImagesDataRepository @Inject constructor(
    private val placeholdersCloudDataSource: PlaceholdersCloudDataSource,
    private val placeholdersLocalDataSource: PlaceholdersLocalDataSource
) : ImagesRepository {

    override fun getPlaceholders(density: ScreenDensity): Single<List<Placeholder>> {
        fun handleLocalResponse(list: List<PlaceholderEntity>) =
            if (list.isNotEmpty()) {
                SingleJust(list)
            } else {
                placeholdersCloudDataSource.getPlaceholders(density)
                    .doOnSuccess { insertOnDatabase(it) }
                    .onErrorReturn { list }
            }

        return placeholdersLocalDataSource.getAll()
            .flatMap { handleLocalResponse(it) }
            .map { it.map { mapToPlaceholder(it) } }
    }

    override fun getLogosForSources(vararg source: String): Single<Map<String, String>> =
        Single.just(source.map { it to "${EndpointUrls.logoApiUrl}$it" }.toMap())

    private fun insertOnDatabase(list: List<PlaceholderEntity>) {
        placeholdersLocalDataSource.insertAll(*list.toTypedArray())
    }
}