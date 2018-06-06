package es.mnmapp.aolv.domain.repository

import es.mnmapp.aolv.domain.entity.Placeholder
import io.reactivex.Single

interface ImagesRepository {

    fun getPlaceholders(): Single<List<Placeholder>>

    fun getLogosForSources(vararg source: String): Single<Map<String, String>>
}