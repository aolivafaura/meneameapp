package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.entity.Placeholder
import es.mnmapp.aolv.domain.entity.Section
import es.mnmapp.aolv.domain.repository.DeviceRepository
import es.mnmapp.aolv.domain.repository.ImagesRepository
import es.mnmapp.aolv.domain.repository.NewsRepository
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import org.apache.commons.lang3.StringUtils

/**
 * Created by antoniojoseoliva on 09/07/2017.
 */

class GetNews(
    postExecutionThread: Scheduler,
    workerThread: Scheduler,
    private val newsRepository: NewsRepository,
    private val imagesRepository: ImagesRepository,
    private val deviceRepository: DeviceRepository
) : UseCase<List<New>, Section>(postExecutionThread, workerThread) {

    override fun getUseCaseType() = Type.Flowable

    override fun buildFlowableUseCase(params: Section): Flowable<List<New>> =
        getTheGoodCall(params).flatMap { enrichInformation(it) }

    private fun getTheGoodCall(section: Section) =
        when (section) {
            Section.Breaking,
            Section.Hot,
            Section.TopVisited -> newsRepository.getTopVisited()
            Section.Popular -> newsRepository.getPopular()
        }

    private fun enrichInformation(originalList: List<New>): Flowable<List<New>> {
        return enrichWithLogos(originalList)
            .compose(applySingleWorkerSchedulers())
            .onErrorReturn { emptyMap() }
            .flatMap { logosMap ->
                val listToEmit = mutableListOf<New>()

                originalList.forEach {
                    listToEmit.add(it.copy(logoUrl = logosMap[it.from] ?: ""))
                }

                enrichWithPlaceholders(listToEmit)
            }
            .onErrorReturn { emptyList() }
            .flatMap { list ->
                val categoriesMap = list.map { it.category to it }.toMap()
                val listToEmit = mutableListOf<New>()

                originalList.forEach {
                    val imageUrl = if (it.thumb.isBlank()) {
                        categoriesMap[normalizeCategory(it.category)]?.url ?: ""
                    } else {
                        it.thumb
                    }
                    listToEmit.add(it.copy(thumb = imageUrl))
                }

                Single.just(listToEmit.toList())
            }
            .toFlowable()
    }

    private fun enrichWithLogos(originalList: List<New>): Single<Map<String, String>> {
        return imagesRepository.getLogosForSources(*originalList.map { it.from }.toTypedArray())
    }

    private fun enrichWithPlaceholders(originalList: List<New>): Single<List<Placeholder>> {
        return if (originalList.asSequence().any { it.thumb.isBlank() }) {
            imagesRepository.getPlaceholders(deviceRepository.getScreenDensity())
        } else {
            Single.just(emptyList())
        }
    }

    private fun normalizeCategory(category: String) = StringUtils.stripAccents(category).toLowerCase()
}
