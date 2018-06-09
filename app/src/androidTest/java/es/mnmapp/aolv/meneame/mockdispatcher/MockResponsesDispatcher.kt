package es.mnmapp.aolv.meneame.mockdispatcher

import android.content.res.AssetManager
import es.mnmapp.aolv.meneame.utils.getJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockResponsesDispatcher(private val assetManager: AssetManager) : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse =
        when (request.path) {
            "/list.php?popular=true" -> MockResponse().setResponseCode(200).setBody(
                assetManager.getJson(
                    "popular_success.json"
                )
            )
            else -> MockResponse().setResponseCode(404)
        }
}