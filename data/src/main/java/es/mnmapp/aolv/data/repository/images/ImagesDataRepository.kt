package es.mnmapp.aolv.data.repository.images

import es.mnmapp.aolv.data.EndpointUrls
import es.mnmapp.aolv.data.entity.PlaceholderEntity
import es.mnmapp.aolv.data.entity.mapToPlaceholder
import es.mnmapp.aolv.data.repository.images.datasource.cloud.PlaceholdersCloudDataSource
import es.mnmapp.aolv.data.repository.images.datasource.local.PlaceholdersLocalDataSource
import es.mnmapp.aolv.domain.entity.Placeholder
import es.mnmapp.aolv.domain.repository.ImagesRepository
import io.reactivex.Single
import io.reactivex.internal.operators.single.SingleJust


class ImagesDataRepository(
        private val placeholdersCloudDataSource: PlaceholdersCloudDataSource,
        private val placeholdersLocalDataSource: PlaceholdersLocalDataSource
) : ImagesRepository {

    override fun getPlaceholders(): Single<List<Placeholder>> {
        fun handleLocalResponse(list: List<PlaceholderEntity>) =
                if (list.isNotEmpty()) {
                    SingleJust(list)
                } else {
                    placeholdersCloudDataSource.getPlaceholders()
                            .doOnSuccess { insertOnDatabase(it) }
                            .onErrorReturn { list }
                }

        return placeholdersLocalDataSource.getAll()
                .flatMap { handleLocalResponse(it) }
                .map { it.map { mapToPlaceholder(it) } }
    }

    override fun getLogosForSources(vararg source: String): Single<Map<String, String>> =
            Single.just(source.map { it to "${EndpointUrls.logoApiUrl}$it" }.toMap())

    private fun insertOnDatabase(list: List<PlaceholderEntity>) {
        placeholdersLocalDataSource.insertAll(*list.toTypedArray())
    }
}