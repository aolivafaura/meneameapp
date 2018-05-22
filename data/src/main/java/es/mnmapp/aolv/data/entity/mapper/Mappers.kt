package es.mnmapp.aolv.data.entity.mapper

import es.mnmapp.aolv.data.entity.NewDto
import es.mnmapp.aolv.domain.entity.New

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

fun fromNewEntityToNew(newDto: NewDto) = New(
        newDto.id ?: 0L,
        newDto.url ?: "",
        newDto.title ?: "",
        newDto.thumb ?: "",
        newDto.from ?: "",
        newDto.votes ?: -1,
        newDto.negatives ?: -1,
        newDto.karma ?: -1,
        newDto.comments ?: -1,
        newDto.sentDate ?: -1L,
        newDto.date ?: -1L,
        newDto.tags ?: ""
)
