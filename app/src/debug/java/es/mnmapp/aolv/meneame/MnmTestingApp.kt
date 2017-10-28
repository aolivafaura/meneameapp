package es.mnmapp.aolv.meneame

import es.mnmapp.aolv.meneame.utils.Lg
import javax.inject.Inject

/**
 * Created by antoniojoseoliva on 08/08/2017.
 */

class MnmTestingApp : MnmApp() {

    @Inject lateinit var logger : Lg

    override fun onCreate() {
        super.onCreate()

        logger.d("APPLICATION ON TESTING MODE")
    }
}