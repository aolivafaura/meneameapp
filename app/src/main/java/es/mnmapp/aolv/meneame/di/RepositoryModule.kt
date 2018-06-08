package es.mnmapp.aolv.meneame.di

import com.google.firebase.firestore.FirebaseFirestore
import es.mnmapp.aolv.data.EndpointUrls
import es.mnmapp.aolv.meneame.di.repositoryproviders.provideCacheDirectory
import es.mnmapp.aolv.meneame.di.repositoryproviders.provideMeneameService
import es.mnmapp.aolv.meneame.di.repositoryproviders.provideHttpClient
import es.mnmapp.aolv.meneame.di.repositoryproviders.providesLocalDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext


/**
 * Created by antonio on 3/11/18.
 */

val repositoryModule = applicationContext {
    bean { provideCacheDirectory(androidApplication().baseContext.cacheDir) }
    bean { getNetworkInterceptors(get()) }
    bean { provideHttpClient(get(), get()) }
    bean { provideMeneameService(get(), EndpointUrls.baseUrl) }
    bean { FirebaseFirestore.getInstance() }
    bean { providesLocalDatabase(androidApplication().baseContext) }
}