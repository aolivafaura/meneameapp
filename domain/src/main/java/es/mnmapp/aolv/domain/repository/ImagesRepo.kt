package es.mnmapp.aolv.domain.repository

import io.reactivex.Flowable

interface ImagesRepo {

    fun getPlaceholderImage(category: String): Flowable<String>
}