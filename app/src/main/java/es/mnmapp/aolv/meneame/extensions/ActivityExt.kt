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

package es.mnmapp.aolv.meneame.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.ui.BaseActivity

/**
 * Adds given fragment to fragment stack. Transaction commit is done allowing state loss.
 *
 * @see FragmentManager.beginTransaction
 *
 * @param[fragment] fragment to add
 * @param[sharedView] view to share between fragments (transition animations)
 * @param[addToBackStack] `true` to add to back stack, `false` otherwise
 */
fun AppCompatActivity.initFragment(
    fragment: Fragment,
    sharedView: View? = null,
    addToBackStack: Boolean? = false
) {
    val transaction = this.supportFragmentManager
        .beginTransaction()
        .add(R.id.container, fragment, fragment.javaClass.simpleName)

    sharedView?.let {
        transaction.addSharedElement(it, sharedView.transitionName)
    }
    addToBackStack?.let {
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.simpleName)
    }

    transaction.commitAllowingStateLoss()
}

inline fun <reified T : ViewModel> BaseActivity.viewModel(): Lazy<T> =
    lazy { ViewModelProviders.of(this, this.viewModelFactory)[T::class.java] }