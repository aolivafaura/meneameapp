package es.mnmapp.aolv.data.repository.placeholders

import es.mnmapp.aolv.data.entity.PlaceholderEntity
import es.mnmapp.aolv.data.entity.mapToPlaceholder
import es.mnmapp.aolv.data.repository.placeholders.datasource.cloud.PlaceholdersCloudDataSource
import es.mnmapp.aolv.data.repository.placeholders.datasource.local.PlaceholdersLocalDataSource
import es.mnmapp.aolv.domain.entity.Placeholder
import es.mnmapp.aolv.domain.repository.PlaceholdersRepository
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableCreate
import io.reactivex.internal.operators.single.SingleJust
import io.reactivex.schedulers.Schedulers


class PlaceholdersDataRepository(
        private val placeholdersCloudDataSource: PlaceholdersCloudDataSource,
        private val placeholdersLocalDataSource: PlaceholdersLocalDataSource
) : PlaceholdersRepository {

    override fun getPlaceholders(): Single<List<Placeholder>> {
        fun handleLocalSuccess(list: List<PlaceholderEntity>) =
                if (list.isNotEmpty()) {
                    SingleJust(list)
                } else {
                    placeholdersCloudDataSource.getPlaceholders()
                            .doOnSuccess { insertOnDatabase(it) }
                            .onErrorReturn { list }
                }

        return placeholdersLocalDataSource.getAll()
                .flatMap { handleLocalSuccess(it) }
                .map { it.map { mapToPlaceholder(it) } }
    }

    private fun insertOnDatabase(list: List<PlaceholderEntity>) {
        CompletableCreate { placeholdersLocalDataSource.insertAll(*list.toTypedArray()) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}