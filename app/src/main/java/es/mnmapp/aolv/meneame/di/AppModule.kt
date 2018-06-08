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

package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.meneame.connectivity.Connectivity
import es.mnmapp.aolv.meneame.utils.Validator
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module.applicationContext

const val KOIN_BEAN_UI_THREAD = "uiThread"
const val KOIN_BEAN_WORKER_THREAD = "workerThread"

/**
 * General app modules definition
 */
val appModule = applicationContext {
    // Schedulers
    bean<Scheduler>(KOIN_BEAN_UI_THREAD) { AndroidSchedulers.mainThread() }
    bean(KOIN_BEAN_WORKER_THREAD) { Schedulers.io() }

    // Connectivity
    bean { Connectivity(get()) }

    // Validations
    bean { Validator }
}
