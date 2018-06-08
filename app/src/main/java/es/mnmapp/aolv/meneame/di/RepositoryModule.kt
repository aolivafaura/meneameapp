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

import com.google.firebase.firestore.FirebaseFirestore
import es.mnmapp.aolv.data.EndpointUrls
import es.mnmapp.aolv.meneame.di.repositoryproviders.provideCacheDirectory
import es.mnmapp.aolv.meneame.di.repositoryproviders.provideHttpClient
import es.mnmapp.aolv.meneame.di.repositoryproviders.provideMeneameService
import es.mnmapp.aolv.meneame.di.repositoryproviders.providesLocalDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

/**
 * Repository module definitions
 */
val repositoryModule = applicationContext {
    bean { provideCacheDirectory(androidApplication().baseContext.cacheDir) }
    bean { getNetworkInterceptors(get()) }
    bean { provideHttpClient(get(), get()) }
    bean { provideMeneameService(get(), EndpointUrls.baseUrl) }
    bean { FirebaseFirestore.getInstance() }
    bean { providesLocalDatabase(androidApplication().baseContext) }
}
