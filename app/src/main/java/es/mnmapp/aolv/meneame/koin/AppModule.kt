package es.mnmapp.aolv.meneame.koin

import es.mnmapp.aolv.meneame.loggers.AnalitycsLogger
import es.mnmapp.aolv.meneame.utils.Connectivity
import es.mnmapp.aolv.meneame.utils.Lg
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
    bean { AnalitycsLogger(get()) }
    bean { Lg(get()) }
}