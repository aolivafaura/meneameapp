package es.mnmapp.aolv.data.repository.news

import es.mnmapp.aolv.data.repository.news.cloud.NewsCloudRepo
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.repository.NewsRepo
import io.reactivex.Flowable

/**
 * This class should decide between available data sources.
 * For the moment, we have just cloud data source, so it's an easy choice.
 * Created by antoniojoseoliva on 08/07/2017.
 */
class NewsDataRepo(private val newsCloudRepo: NewsCloudRepo) : NewsRepo {

    override fun getPopular(): Flowable<List<New>> = newsCloudRepo.getPopular()

    override fun getTopVisited(): Flowable<List<New>> = newsCloudRepo.getTopVisited()
}
