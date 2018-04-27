package es.mnmapp.aolv.meneame.di.repositoryproviders

import es.mnmapp.aolv.data.net.MeneameService
import okhttp3.OkHttpClient

fun createMeneameService(okHttpClient: OkHttpClient) = MeneameService.create(okHttpClient)