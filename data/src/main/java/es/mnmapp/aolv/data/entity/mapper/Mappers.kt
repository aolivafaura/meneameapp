package es.mnmapp.aolv.data.entity.mapper

import es.mnmapp.aolv.data.entity.NewDto
import es.mnmapp.aolv.domain.entity.New

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

fun fromNewEntityToNew(newDto: NewDto) =
        New(newDto.id,
        newDto.url,
        newDto.title,
        newDto.thumb)
