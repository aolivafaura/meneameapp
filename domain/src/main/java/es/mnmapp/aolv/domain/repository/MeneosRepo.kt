package es.mnmapp.aolv.domain.repository

import es.mnmapp.aolv.domain.entity.Meneo
import io.reactivex.Observable

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */
interface MeneosRepo {

    fun getPopular() : Observable<List<Meneo>>

    fun getTopVisited() : Observable<List<Meneo>>
}
