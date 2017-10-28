package es.mnmapp.aolv.data.entity.mapper

import es.mnmapp.aolv.data.entity.MeneoEntity
import es.mnmapp.aolv.domain.entity.Meneo

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

fun fromMeneoEntityToMeneo(meneoEntity: MeneoEntity) =
        Meneo(meneoEntity.id,
        meneoEntity.url,
        meneoEntity.title,
        meneoEntity.thumb)
