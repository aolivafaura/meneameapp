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

package es.mnmapp.aolv.meneame.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import es.mnmapp.aolv.data.testing.SimpleIdlingResource
import es.mnmapp.aolv.domain.UI_THREAD
import es.mnmapp.aolv.domain.WORKER_THREAD
import es.mnmapp.aolv.meneame.MnmApp
import es.mnmapp.aolv.meneame.connectivity.Connectivity
import es.mnmapp.aolv.meneame.ui.Navigation
import es.mnmapp.aolv.meneame.utils.Validator
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    lateinit var context: Context

    @Provides
    @Singleton
    fun provideContext(app: MnmApp): Context {
        this.context = app.applicationContext!!
        return context
    }

    @Provides
    @Named(UI_THREAD)
    fun provideUIThread(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(WORKER_THREAD)
    fun provideWorkerThread(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    fun provideConnectivityManager(): Connectivity = Connectivity(
        context,
        AndroidSchedulers.mainThread()
    )

    @Provides
    @Singleton
    fun provideValidator(): Validator = Validator

    @Provides
    @Singleton
    fun provideNavigation() = Navigation()

    @Provides
    @Singleton
    fun provideIdlingResources() = SimpleIdlingResource()

    @Provides
    @Singleton
    fun provideResources() = context.resources!!
}