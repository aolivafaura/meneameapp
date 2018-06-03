package es.mnmapp.aolv.domain.repository

import es.mnmapp.aolv.domain.entity.New
import io.reactivex.Flowable

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */
interface NewsRepository {

    fun getPopular(): Flowable<List<New>>

    fun getTopVisited(): Flowable<List<New>>
}
