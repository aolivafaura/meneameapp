package es.mnmapp.aolv.meneame.koin

import es.mnmapp.aolv.data.net.MeneameService
import es.mnmapp.aolv.meneame.koin.repositoryproviders.createCacheDirectory
import es.mnmapp.aolv.meneame.koin.repositoryproviders.createOkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

/**
 * Created by antonio on 3/11/18.
 */

val repositoryModule = applicationContext {
    bean { createCacheDirectory(androidApplication().baseContext.cacheDir) }
    bean { getNetworkInterceptors(get()) }
    bean { createOkHttpClient(get(), get()) }
    bean { MeneameService.create(get()) }
}