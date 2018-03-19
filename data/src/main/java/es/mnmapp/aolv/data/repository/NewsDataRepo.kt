package es.mnmapp.aolv.data.repository

import es.mnmapp.aolv.data.net.MeneameService
import es.mnmapp.aolv.data.repository.cloud.NewsCloudRepo
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.repository.NewsRepo
import io.reactivex.Flowable

/**
 * This class should decide between available data sources.
 * For the moment, we have just cloud data source, so it's an easy choice.
 * Created by antoniojoseoliva on 08/07/2017.
 */
class NewsDataRepo(private val meneameService: MeneameService) : NewsRepo {

    override fun getPopular(): Flowable<List<New>> {
        return NewsCloudRepo(meneameService).getPopular()
    }

    override fun getTopVisited(): Flowable<List<New>> {
        return NewsCloudRepo(meneameService).getTopVisited()
    }
}
