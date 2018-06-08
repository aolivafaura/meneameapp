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

package es.mnmapp.aolv.meneame.entity

import es.mnmapp.aolv.domain.entity.New

/**
 * Representation of new cell on news lists
 */
data class NewCellUi(
    val id: Long,
    val url: String,
    val title: String,
    val source: String,
    val thumb: String,
    val positiveVotes: Int,
    val negativeVotes: Int,
    val karma: Int,
    val comments: Int,
    val date: Long,
    val logoUrl: String
)

/**
 * Mapper
 *
 * @param[new] transformation target
 *
 * @return transformation result
 */
fun fromNewToNewCellUi(new: New): NewCellUi =
    NewCellUi(
        new.id,
        new.url,
        new.title,
        new.from,
        new.thumb,
        new.positiveVotes,
        new.negativeVotes,
        new.karma,
        new.comments,
        new.date,
        new.logoUrl ?: ""
    )
