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

package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import es.mnmapp.aolv.meneame.extensions.initFragment
import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListFragment
import es.mnmapp.aolv.meneame.ui.view.newsviewer.NewsViewerFragment

/**
 * In charge of pass information and transitions between fragments.
 */
class Navigation {

    // Class methods -----

    /**
     * Navigates to news list, adding it to back stack.
     *
     * @param[activity] Activity
     */
    fun navigateToNewsList(activity: BaseActivity) {
        activity.initFragment(NewsListFragment(), addToBackStack = true)
    }

    /**
     * Navigates to news detail, adding it to back stack.
     *
     * @param[activity] Activity
     * @param[url] Web url
     * @param[title] Fragment title
     */
    fun navigateToNewsDetail(activity: BaseActivity, url: String?, title: String?) {
        val bundle = Bundle().apply {
            putString(NewsViewerFragment.KEY_URL, url)
            putString(NewsViewerFragment.KEY_TITLE, title)
        }

        val fragment = NewsViewerFragment().apply { arguments = bundle }
        activity.initFragment(fragment, addToBackStack = true)
    }
}
