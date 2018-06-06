package es.mnmapp.aolv.data.repository.images.datasource.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import es.mnmapp.aolv.data.entity.PlaceholderEntity

@Entity(tableName = "placeholder")
data class PlaceholderRoom(@PrimaryKey @ColumnInfo(name = "category") val category: String,
                           @ColumnInfo(name = "url") val url: String
)

fun mapToPlaceholderEntity(placeholderRoom: PlaceholderRoom) =
        PlaceholderEntity(
                placeholderRoom.category,
                placeholderRoom.url
        )

fun mapFromPlaceholderEntity(placeholderEntity: PlaceholderEntity) =
        PlaceholderRoom(
                placeholderEntity.category,
                placeholderEntity.url
        )