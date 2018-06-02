package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.data.repository.news.cloud.NewsCloudRepo
import es.mnmapp.aolv.meneame.constants.EndpointUrls
import es.mnmapp.aolv.meneame.di.repositoryproviders.createCacheDirectory
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
    bean { NewsCloudRepo(get(), get()) }
}