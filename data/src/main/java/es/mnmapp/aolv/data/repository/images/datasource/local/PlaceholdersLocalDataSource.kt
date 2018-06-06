package es.mnmapp.aolv.data.repository.images.datasource.local

import android.arch.persistence.room.Transaction
import es.mnmapp.aolv.data.entity.PlaceholderEntity
import es.mnmapp.aolv.data.testing.SimpleIdlingResource
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableCreate
import io.reactivex.schedulers.Schedulers

class PlaceholdersLocalDataSource(private val idlingResource: SimpleIdlingResource,
                                  private val placeholdersRoomDao: PlaceholdersRoomDao) {

    fun getAll(): Single<List<PlaceholderEntity>> {
        idlingResource.setIdleState(false)
        return placeholdersRoomDao.getAll().map {
            idlingResource.setIdleState(true)
            it.map { mapToPlaceholderEntity(it) }
        }
    }

    @Transaction
    fun insertAll(vararg placeholderEntity: PlaceholderEntity) {
        CompletableCreate {
            placeholdersRoomDao.insertAll(*placeholderEntity.map { mapFromPlaceholderEntity(it) }.toTypedArray())
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}