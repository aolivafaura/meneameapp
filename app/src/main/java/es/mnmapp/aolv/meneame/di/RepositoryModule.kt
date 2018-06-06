package es.mnmapp.aolv.meneame.di

import android.content.res.Configuration
import com.google.firebase.firestore.FirebaseFirestore
import es.mnmapp.aolv.data.EndpointUrls
import es.mnmapp.aolv.meneame.di.repositoryproviders.createCacheDirectory
import es.mnmapp.aolv.meneame.di.repositoryproviders.createLocalDatabase
import es.mnmapp.aolv.meneame.di.repositoryproviders.createMeneameService
import es.mnmapp.aolv.meneame.di.repositoryproviders.createOkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext


/**
 * Created by antonio on 3/11/18.
 */

val repositoryModule = applicationContext {
    bean { createCacheDirectory(androidApplication().baseContext.cacheDir) }
    bean { getNetworkInterceptors(get()) }
    bean { createOkHttpClient(get(), get()) }
    bean { createMeneameService(get(), EndpointUrls.baseUrl) }
    bean { FirebaseFirestore.getInstance() }
    bean("placeholdersDb") { createLocalDatabase(androidApplication()).placeholdersDao() }
    bean("screenDensity") {
        androidApplication().baseContext.resources.displayMetrics.density
    }
    bean("screenSize") {
        androidApplication().baseContext.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
    }
}