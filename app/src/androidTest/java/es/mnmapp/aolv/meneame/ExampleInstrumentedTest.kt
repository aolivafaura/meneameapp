package es.mnmapp.aolv.meneame

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class) class ExampleInstrumentedTest {

    @Test fun testUseAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        Log.d("", appContext.packageName)
        assertEquals("es.mnmapp.aolv.meneame.dev", appContext.packageName)
    }
}
