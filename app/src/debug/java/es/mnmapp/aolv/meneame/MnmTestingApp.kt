package es.mnmapp.aolv.meneame

import es.mnmapp.aolv.meneame.utils.d

/**
 * Created by antoniojoseoliva on 08/08/2017.
 */

class MnmTestingApp : MnmApp() {

    override fun onCreate() {
        super.onCreate()

        d("APPLICATION ON TESTING MODE")
    }
}