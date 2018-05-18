package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.meneame.loggers.AnalitycsLogger
import es.mnmapp.aolv.meneame.loggers.Logger
import es.mnmapp.aolv.meneame.utils.Connectivity
import es.mnmapp.aolv.meneame.utils.Validator
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module.applicationContext

/**
 * Created by antonio on 3/18/18.
 */

val appModule = applicationContext {
    bean("uiThread") { AndroidSchedulers.mainThread() as Scheduler }
    bean("workerThread") { Schedulers.io() }
    bean { Connectivity(get()) }
    bean { Logger() }
    bean { AnalitycsLogger(get()) }
    bean { Validator }
}