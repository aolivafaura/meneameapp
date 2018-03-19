package es.mnmapp.aolv.meneame.koin.repositoryproviders

import es.mnmapp.aolv.data.net.MeneameService
import okhttp3.OkHttpClient

/**
 * Created by antonio on 3/11/18.
 */

fun createMeneameWebService(client: OkHttpClient) = MeneameService.create(client)