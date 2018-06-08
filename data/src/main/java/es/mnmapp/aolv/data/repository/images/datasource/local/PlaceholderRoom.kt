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

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import es.mnmapp.aolv.data.entity.PlaceholderEntity

@Entity(tableName = "placeholder")
data class PlaceholderRoom(
    @PrimaryKey @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "url") val url: String
)

fun mapToPlaceholderEntity(placeholderRoom: PlaceholderRoom): PlaceholderEntity =
    PlaceholderEntity(
        placeholderRoom.category,
        placeholderRoom.url
    )

fun mapFromPlaceholderEntity(placeholderEntity: PlaceholderEntity): PlaceholderRoom =
    PlaceholderRoom(
        placeholderEntity.category,
        placeholderEntity.url
    )