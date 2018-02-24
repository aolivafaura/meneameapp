package es.mnmapp.aolv.domain.repository

import es.mnmapp.aolv.domain.entity.New
import io.reactivex.Observable

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */
interface NewsRepo {

    fun getPopular(): Observable<List<New>>

    fun getTopVisited(): Observable<List<New>>
}
