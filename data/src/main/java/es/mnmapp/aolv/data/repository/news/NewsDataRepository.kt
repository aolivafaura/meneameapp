package es.mnmapp.aolv.data.repository.news

import es.mnmapp.aolv.data.repository.news.datasource.cloud.NewsCloudDataSource
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.repository.NewsRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * This class should decide between available data sources.
 * For the moment, we have just cloud data source, so it's an easy choice.
 * Created by antoniojoseoliva on 08/07/2017.
 */
class NewsDataRepository @Inject constructor(
    private val newsCloudDataSource: NewsCloudDataSource
) : NewsRepository {

    override fun getPopular(): Flowable<List<New>> = newsCloudDataSource.getPopular()

    override fun getTopVisited(): Flowable<List<New>> = newsCloudDataSource.getTopVisited()
}
