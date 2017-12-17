package es.mnmapp.aolv.meneame.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import es.mnmapp.aolv.meneame.MnmApp
import javax.inject.Singleton

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (AppModule::class),
    (BuildersModule::class),
    (RepositoryModule::class),
    (NetworkInterceptorsModule::class),
    (ApiModule::class),
    (HttpClientModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: MnmApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: MnmApp)
}
