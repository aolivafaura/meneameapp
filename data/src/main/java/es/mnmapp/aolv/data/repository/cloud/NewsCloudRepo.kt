package es.mnmapp.aolv.data.repository.cloud

import es.mnmapp.aolv.data.entity.mapper.fromNewEntityToNew
import es.mnmapp.aolv.data.net.MeneameService
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.repository.NewsRepo
import io.reactivex.Flowable

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */

class NewsCloudRepo(private val meneameService: MeneameService) : NewsRepo {

    override fun getPopular(): Flowable<List<New>> {
        val options = HashMap<String, String>().apply {
            put("popular", "true")
        }
        return meneameService.getMeneos(options).map {
            it.objects.map { fromNewEntityToNew(it) }
        }
    }

    override fun getTopVisited(): Flowable<List<New>> {
        val options = HashMap<String, String>().apply {
            put("top_visited", "true")
        }
        return meneameService.getMeneos(options).map {
            it.objects.map { fromNewEntityToNew(it) }
        }
    }
}
