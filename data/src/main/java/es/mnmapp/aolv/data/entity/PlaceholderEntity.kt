package es.mnmapp.aolv.data.entity

import es.mnmapp.aolv.domain.entity.Placeholder

data class PlaceholderEntity(val category: String,
                             val url: String)

fun mapToPlaceholder(placeholderEntity: PlaceholderEntity) =
        Placeholder(
                placeholderEntity.category,
                placeholderEntity.url
        )