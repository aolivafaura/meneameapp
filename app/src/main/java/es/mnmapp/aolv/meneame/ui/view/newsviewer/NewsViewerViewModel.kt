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

package es.mnmapp.aolv.meneame.ui.view.newsviewer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.meneame.utils.Validator
import javax.inject.Inject

/**
 * View model for detail fragment
 *
 * @see NewsViewerFragment
 *
 * @param[validator] validator
 */
class NewsViewerViewModel @Inject constructor(
    private val validator: Validator
) : ViewModel() {

    // Fields -----

    val url = MutableLiveData<String?>()
    val title = MutableLiveData<String>()

    // Class methods -----

    /**
     * Validates and set given url if valid or `null` if not valid
     *
     * @param[url] web url
     */
    fun setUrl(url: String?) {
        this.url.value = if (validator.isValidUrl(url)) url else null
    }

    /**
     * @param[title] fragment title
     */
    fun setTitle(title: String?) {
        this.title.value = title ?: ""
    }
}