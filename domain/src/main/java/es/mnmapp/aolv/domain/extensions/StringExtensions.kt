package es.mnmapp.aolv.domain.extensions

import org.apache.commons.lang3.StringUtils

fun String.stripAccents(): String = StringUtils.stripAccents(this)