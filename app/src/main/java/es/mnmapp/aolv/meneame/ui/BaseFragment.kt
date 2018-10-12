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

package es.mnmapp.aolv.meneame.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antoniooliva.logger.Lgr
import dagger.android.support.AndroidSupportInjection
import es.mnmapp.aolv.meneame.dagger.videmodel.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Base fragment. Every fragment must extend this class.
 */
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    // Fields -----

    private val disposables = CompositeDisposable()

    // Fragment overrides -----

    override fun onResume() {
        super.onResume()
        Lgr.get().setCurrentScreen(activity!!, getAnalyticsName())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    // Abstract methods -----

    /**
     * @return layout associated with fragment
     */
    @LayoutRes
    abstract fun getFragmentLayout(): Int

    /**
     * @return screen view name to report to analytics
     */
    abstract fun getAnalyticsName(): String
}
