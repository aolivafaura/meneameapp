package es.mnmapp.aolv.meneame

/**
 * Created by antoniojoseoliva on 09/08/2017.
 */

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

/**
 * Created by antoniojoseoliva on 08/08/2017.
 */
class TestRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class,
            IllegalAccessException::class,
            ClassNotFoundException::class) override fun newApplication(cl : ClassLoader,
                                                                       className : String,
                                                                       context : Context) : Application {
        return super.newApplication(cl, MnmTestingApp::class.java.name, context)
    }
}