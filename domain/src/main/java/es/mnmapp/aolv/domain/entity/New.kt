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

package es.mnmapp.aolv.domain.entity

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */

data class New(
    val id: Long,
    val url: String,
    val title: String,
    val category: String,
    val thumb: String,
    val from: String,
    val positiveVotes: Int,
    val negativeVotes: Int,
    val karma: Int,
    val comments: Int,
    val sendDate: Long,
    val date: Long,
    val tags: String,
    var logoUrl: String? = ""
)

enum class Section { Breaking, Hot, Popular, TopVisited }