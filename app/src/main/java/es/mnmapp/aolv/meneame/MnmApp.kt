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
import android.app.Application
import com.antoniooliva.logger.Lgr
import com.google.firebase.FirebaseApp
import es.mnmapp.aolv.meneame.di.getKoinModules
import org.koin.android.ext.android.startKoin

/**
 * Application object
 */
@SuppressLint("Registered")
open class MnmApp : Application() {

    // Application overrides -----

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initLogger()
    }

    private fun initKoin() {
        startKoin(this, getKoinModules())
    }

    private fun initLogger() {
        Lgr.initialize(this)
    }
}
