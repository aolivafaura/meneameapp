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

package es.mnmapp.aolv.meneame

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import com.antoniooliva.logger.Lgr
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Application object
 */
@SuppressLint("Registered")
open class MnmApp : Application(), HasActivityInjector {

    // Injection -----

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    // Application overrides -----

    override fun onCreate() {
        super.onCreate()
        initLogger()
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    private fun initLogger() {
        Lgr.initialize(this)
    }

    // HasActivityInjector overrides -----

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector
}
