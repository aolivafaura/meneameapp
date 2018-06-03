package es.mnmapp.aolv.data.repository.placeholders.datasource.local

import es.mnmapp.aolv.data.entity.PlaceholderEntity
import es.mnmapp.aolv.data.testing.SimpleIdlingResource
import io.reactivex.Single

class PlaceholdersLocalDataSource(private val idlingResource: SimpleIdlingResource,
                                  private val placeholdersRoomDao: PlaceholdersRoomDao) {

    fun getAll(): Single<List<PlaceholderEntity>> {
        idlingResource.setIdleState(false)
        return placeholdersRoomDao.getAll().map {
            idlingResource.setIdleState(true)
            it.map { mapToPlaceholderEntity(it) }
        }
    }

    fun insertAll(vararg placeholderEntity: PlaceholderEntity) =
            placeholdersRoomDao
                    .insertAll(*placeholderEntity.map { mapFromPlaceholderEntity(it) }.toTypedArray())
}