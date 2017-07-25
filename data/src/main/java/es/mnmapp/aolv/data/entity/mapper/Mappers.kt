package es.mnmapp.aolv.data.entity.mapper

import es.mnmapp.aolv.data.entity.MeneoEntity
import es.mnmapp.aolv.domain.entity.Meneo

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

fun fromMeneoEntityToMeneo(meneoEntity : MeneoEntity) = Meneo(meneoEntity.id,
                                                              meneoEntity.url,
                                                              meneoEntity.title)

fun fromMeneoEntityListToMeneoList(meneoEntityList : List<MeneoEntity>) : List<Meneo> {

    if (meneoEntityList.isNotEmpty()) {

        val meneoList = ArrayList<Meneo>()
        meneoEntityList.mapTo(meneoList) { fromMeneoEntityToMeneo(it) }

        return meneoList
    }

    return emptyList()
}
