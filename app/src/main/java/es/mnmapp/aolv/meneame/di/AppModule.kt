package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.meneame.connectivity.Connectivity
import es.mnmapp.aolv.meneame.utils.Validator
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module.applicationContext

/**
 * Created by antonio on 3/18/18.
 */

val appModule = applicationContext {
    // Schedulers
    bean<Scheduler>("uiThread") { AndroidSchedulers.mainThread() }
    bean("workerThread") { Schedulers.io() }
    // Connectivity
    bean { Connectivity(get()) }
    // Validations
    bean { Validator }
}