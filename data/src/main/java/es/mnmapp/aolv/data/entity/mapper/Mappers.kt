package es.mnmapp.aolv.data.entity.mapper

import es.mnmapp.aolv.data.entity.NewEntity
import es.mnmapp.aolv.domain.entity.New

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

fun fromNewEntityToNew(newEntity: NewEntity) =
        New(newEntity.id,
        newEntity.url,
        newEntity.title,
        newEntity.thumb)
