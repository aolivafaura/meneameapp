/*
 *     Copyright 2018 @ https://github.com/aolivafaura/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.mnmapp.aolv.meneame

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.webkit.WebViewFragment
import es.mnmapp.aolv.data.EndpointUrls
import es.mnmapp.aolv.meneame.mockdispatcher.MockResponsesDispatcher
import es.mnmapp.aolv.meneame.pageobjects.ListPageObject
import es.mnmapp.aolv.meneame.ui.view.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListFragmentTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private var idlingResource: IdlingResource? = null

    private val server = MockWebServer()

    @Before
    fun before() {
        server.setDispatcher(MockResponsesDispatcher(InstrumentationRegistry.getInstrumentation().context.assets))
        server.start()
        EndpointUrls.baseUrl = server.url("/").toString()

        startActivity()
    }

    @After
    fun after() {
        server.shutdown()
    }

    @Test
    fun firstItemReceivedIsArFirstListPosition() {
        onView(withText("Lo de Rajoy es mucho peor que lo de Cifuentes")).check(matches(isDisplayed()))
    }

    @Test
    fun clickItemOpensDetail() {
        Intents.init()
        ListPageObject.clickOnNewWithPosition(0)

        intended(hasComponent(WebViewFragment::class.java.name))
        Intents.release()
    }

    private fun startActivity() {
        val intent = Intent()
        activityRule.launchActivity(intent)

        IdlingRegistry.getInstance().register(idlingResource)
    }
}