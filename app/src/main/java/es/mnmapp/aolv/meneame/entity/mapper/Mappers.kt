package es.mnmapp.aolv.meneame.entity.mapper

import es.mnmapp.aolv.domain.entity.Meneo
import es.mnmapp.aolv.meneame.entity.MeneoUi

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

fun fromMeneoToMeneoUi(meneo : Meneo) = MeneoUi(meneo.id, meneo.url, meneo.title)
