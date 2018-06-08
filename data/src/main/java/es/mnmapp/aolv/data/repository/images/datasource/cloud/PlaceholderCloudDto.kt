package es.mnmapp.aolv.data.repository.images.datasource.cloud

import com.google.firebase.firestore.DocumentSnapshot
import es.mnmapp.aolv.data.constants.FIREBASE_PLACEHOLDERS_FIELDKEY_CATEGORY
import es.mnmapp.aolv.data.constants.FIREBASE_PLACEHOLDERS_FIELDKEY_URLS
import es.mnmapp.aolv.data.constants.FIREBASE_PLACEHOLDERS_URL_FULL
import es.mnmapp.aolv.data.constants.FIREBASE_PLACEHOLDERS_URL_REGULAR
import es.mnmapp.aolv.data.constants.FIREBASE_PLACEHOLDERS_URL_SMALL
import es.mnmapp.aolv.data.entity.PlaceholderEntity
import es.mnmapp.aolv.domain.repository.ScreenDensity

data class PlaceholderCloudDto(val category: String, val urls: PlaceholderUrls)

data class PlaceholderUrls(val full: String, val regular: String, val small: String)

fun mapToPlaceholderCloudDto(placeholders: List<DocumentSnapshot>) =
    placeholders.map {
        PlaceholderCloudDto(
            it.getString(FIREBASE_PLACEHOLDERS_FIELDKEY_CATEGORY) ?: "",
            @Suppress("unchecked_cast")
            mapToPlaceholderUrls(it.get(FIREBASE_PLACEHOLDERS_FIELDKEY_URLS) as Map<String, String>)
        )
    }

private fun mapToPlaceholderUrls(urls: Map<String, String>): PlaceholderUrls =
    PlaceholderUrls(
        urls[FIREBASE_PLACEHOLDERS_URL_FULL] ?: "",
        urls[FIREBASE_PLACEHOLDERS_URL_REGULAR] ?: "",
        urls[FIREBASE_PLACEHOLDERS_URL_SMALL] ?: ""
    )

fun mapToPlaceholderEntity(cloudDto: PlaceholderCloudDto, density: ScreenDensity): PlaceholderEntity =
    PlaceholderEntity(
        cloudDto.category,
        when (density) {
            ScreenDensity.Low -> cloudDto.urls.small
            ScreenDensity.Medium -> cloudDto.urls.regular
            ScreenDensity.Large -> cloudDto.urls.full
        }
    )