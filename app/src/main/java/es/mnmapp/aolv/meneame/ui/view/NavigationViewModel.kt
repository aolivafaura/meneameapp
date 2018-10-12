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

package es.mnmapp.aolv.meneame.ui.view

import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.Navigation
import javax.inject.Inject

/**
 * View model to manage navigation across the app.
 *
 * @param[navigation] Navigation manager
 */
class NavigationViewModel @Inject constructor(private val navigation: Navigation) : ViewModel() {

    // Class methods -----

    /**
     * Navigates to news list
     *
     * @param[activity] Activity
     */
    fun navigateToNewsList(activity: BaseActivity) {
        navigation.navigateToNewsList(activity)
    }

    /**
     * Navigates to news detail
     *
     * @param[activity] Activity
     * @param[url] Target url
     * @param[title] Title of the new
     */
    fun navigateToNewsDetail(activity: BaseActivity, url: String, title: String) {
        navigation.navigateToNewsDetail(activity, url, title)
    }
}
