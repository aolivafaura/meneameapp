package es.mnmapp.aolv.domain.repository

import es.mnmapp.aolv.domain.entity.Placeholder
import io.reactivex.Single

interface PlaceholdersRepository {

    fun getPlaceholders(): Single<List<Placeholder>>
}