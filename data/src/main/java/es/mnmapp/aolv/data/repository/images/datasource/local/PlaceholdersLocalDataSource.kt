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

package es.mnmapp.aolv.data.repository.images.datasource.local

import android.arch.persistence.room.Transaction
import es.mnmapp.aolv.data.entity.PlaceholderEntity
import es.mnmapp.aolv.data.testing.SimpleIdlingResource
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableCreate
import io.reactivex.schedulers.Schedulers

class PlaceholdersLocalDataSource(
    private val idlingResource: SimpleIdlingResource,
    private val placeholdersRoomDao: PlaceholdersRoomDao
) {

    fun getAll(): Single<List<PlaceholderEntity>> {
        idlingResource.setIdleState(false)
        return placeholdersRoomDao.getAll().map {
            idlingResource.setIdleState(true)
            it.map { mapToPlaceholderEntity(it) }
        }
    }

    @Transaction
    fun insertAll(vararg placeholderEntity: PlaceholderEntity) {
        CompletableCreate {
            placeholdersRoomDao.insertAll(
                *placeholderEntity.map { mapFromPlaceholderEntity(it) }.toTypedArray()
            )
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}