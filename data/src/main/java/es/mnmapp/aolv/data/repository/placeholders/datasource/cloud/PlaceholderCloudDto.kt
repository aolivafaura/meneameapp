package es.mnmapp.aolv.data.repository.placeholders.datasource.cloud

import com.google.firebase.firestore.DocumentSnapshot
import es.mnmapp.aolv.data.constants.*
import es.mnmapp.aolv.data.entity.PlaceholderEntity

data class PlaceholderCloudDto(val category: String,
                               val urls: PlaceholderUrls)

data class PlaceholderUrls(val full: String,
                           val regular: String,
                           val small: String)

fun mapToPlaceholderCloudDto(placeholders: List<DocumentSnapshot>) =
        placeholders.map {
            PlaceholderCloudDto(
                    it.getString(FIREBASE_PLACEHOLDERS_FIELDKEY_CATEGORY) ?: "",
                    @Suppress("UNCHECKED_CAST")
                    mapToPlaceholderUrls(it.get(FIREBASE_PLACEHOLDERS_FIELDKEY_URLS) as Map<String, String>)
            )
        }

private fun mapToPlaceholderUrls(urls: Map<String, String>) =
        PlaceholderUrls(
                urls[FIREBASE_PLACEHOLDERS_URL_FULL] ?: "",
                urls[FIREBASE_PLACEHOLDERS_URL_REGULAR] ?: "",
                urls[FIREBASE_PLACEHOLDERS_URL_SMALL] ?: ""
        )

fun mapToPlaceholderEntity(placeholderCloudDto: PlaceholderCloudDto) =
        PlaceholderEntity(
                placeholderCloudDto.category,
                placeholderCloudDto.urls.regular
        )