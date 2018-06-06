package es.mnmapp.aolv.meneame.di.repositoryproviders

import es.mnmapp.aolv.data.repository.news.datasource.cloud.MeneameService
import okhttp3.OkHttpClient

fun createMeneameService(okHttpClient: OkHttpClient, baseUrl: String): MeneameService =
        MeneameService.create(okHttpClient, baseUrl)