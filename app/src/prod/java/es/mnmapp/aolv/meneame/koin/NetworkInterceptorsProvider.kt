package es.mnmapp.aolv.meneame.koin

import es.mnmapp.aolv.meneame.interceptors.CacheInterceptor
import es.mnmapp.aolv.meneame.utils.Connectivity

/**
 * Created by antonio on 3/11/18.
 */

fun getNetworkInterceptors(connectivity: Connectivity) =
        arrayListOf(getCacheInterceptor(connectivity))

private fun getCacheInterceptor(connectivity: Connectivity) = CacheInterceptor(connectivity)
