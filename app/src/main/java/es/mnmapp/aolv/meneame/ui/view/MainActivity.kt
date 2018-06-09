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

import android.os.Bundle
import es.mnmapp.aolv.meneame.ui.BaseActivity
import org.koin.android.architecture.ext.viewModel

/**
 * Main activity. This is the only one activity on the project, and is used to manage interactions
 * between fragments and navigation.
 */
class MainActivity : BaseActivity() {

    // Fields -----

    private val mainViewModel by viewModel<NavigationViewModel>()

    // Activity overrides -----

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            mainViewModel.navigateToNewsList(this)
        }
    }
}
