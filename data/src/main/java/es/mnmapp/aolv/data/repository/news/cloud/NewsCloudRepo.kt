package es.mnmapp.aolv.data.repository.news.cloud

import es.mnmapp.aolv.data.entity.mapper.fromNewEntityToNew
import es.mnmapp.aolv.data.net.MeneameService
import es.mnmapp.aolv.data.testing.SimpleIdlingResource
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.repository.NewsRepo
import io.reactivex.Flowable

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */

class NewsCloudRepo(
        private val idlingResource: SimpleIdlingResource,
        private val meneameService: MeneameService
) : NewsRepo {

    override fun getPopular() = getFromCloud("popular")

    override fun getTopVisited() = getFromCloud("top_visited")

    private fun getFromCloud(category: String): Flowable<List<New>> {
        idlingResource.setIdleState(false)

        val options = HashMap<String, String>().apply { put(category, "true") }
        return meneameService.getMeneos(options).map {
            idlingResource.setIdleState(true)
            it.objects.map { fromNewEntityToNew(it) }
        }
    }
}
