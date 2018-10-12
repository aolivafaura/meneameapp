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

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import es.mnmapp.aolv.meneame.MnmApp
import es.mnmapp.aolv.meneame.dagger.videmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidSupportInjectionModule::class),
        (ActivityBuilderModule::class),
        (ViewModelModule::class),
        (AppModule::class),
        (RepositoryModule::class)
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: MnmApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: MnmApp)
}