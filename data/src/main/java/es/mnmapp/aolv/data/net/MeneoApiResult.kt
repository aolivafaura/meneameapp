package es.mnmapp.aolv.data.net

import es.mnmapp.aolv.data.entity.NewEntity

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */
data class MeneoApiResult(val url: String, val objects: MutableList<NewEntity>)
