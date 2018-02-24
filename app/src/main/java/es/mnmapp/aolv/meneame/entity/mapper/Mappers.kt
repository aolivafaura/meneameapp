package es.mnmapp.aolv.meneame.entity.mapper

import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.meneame.entity.NewUi

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

fun fromNewToNewUi(aNew: New) = NewUi(aNew.id, aNew.url, aNew.title, aNew.thumb)
