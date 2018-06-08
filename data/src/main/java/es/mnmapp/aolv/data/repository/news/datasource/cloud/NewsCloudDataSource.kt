package es.mnmapp.aolv.data.repository.news.datasource.cloud

import es.mnmapp.aolv.data.entity.mapToNew
import es.mnmapp.aolv.data.testing.SimpleIdlingResource
import es.mnmapp.aolv.domain.entity.New
import io.reactivex.Flowable

class NewsCloudDataSource(
    private val idlingResource: SimpleIdlingResource,
    private val meneameService: MeneameService
) {

    fun getPopular() = getFromCloud("top_visited")

    fun getTopVisited() = getFromCloud("popular")

    private fun getFromCloud(category: String): Flowable<List<New>> {
        idlingResource.setIdleState(false)

        val options = HashMap<String, String>().apply { put(category, "true") }
        return meneameService.getMeneos(options).map {
            idlingResource.setIdleState(true)
            it.objects.map { mapToNew(it) }
        }
    }
}
