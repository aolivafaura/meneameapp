package es.mnmapp.aolv.data.repository.news.datasource.cloud

import es.mnmapp.aolv.data.entity.NewDto

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */
data class MeneoApiResultDto(val url: String, val objects: MutableList<NewDto>)
