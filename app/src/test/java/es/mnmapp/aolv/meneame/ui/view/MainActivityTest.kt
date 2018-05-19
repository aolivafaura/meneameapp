package es.mnmapp.aolv.meneame.ui.view

import es.mnmapp.aolv.meneame.ui.view.newslist.NewsListFragment
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Test
    fun `Given application initialized, when main activity is created, then news list fragment is created`() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)

        val fragment = activity.supportFragmentManager.findFragmentByTag(NewsListFragment::class.java.simpleName)
        assertNotNull(fragment)
    }
}