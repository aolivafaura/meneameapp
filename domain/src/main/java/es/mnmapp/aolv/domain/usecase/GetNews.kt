package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.entity.Section
import es.mnmapp.aolv.domain.repository.DeviceRepository
import es.mnmapp.aolv.domain.repository.ImagesRepository
import es.mnmapp.aolv.domain.repository.NewsRepository
import io.reactivex.Flowable
import io.reactivex.Scheduler
import org.apache.commons.lang3.StringUtils

/**
 * Created by antoniojoseoliva on 09/07/2017.
 */

class GetNews(
    postExecutionThread: Scheduler,
    private val workerThread: Scheduler,
    private val newsRepository: NewsRepository,
    private val imagesRepository: ImagesRepository,
    private val deviceRepository: DeviceRepository
) : UseCase<List<New>, Section>(postExecutionThread, workerThread) {

    override fun getUseCaseType() = Type.Flowable

    override fun buildFlowableUseCase(params: Section): Flowable<List<New>> {
        return getTheGoodCall(params).flatMap {
            enrichInformation(it)
        }
    }

    private fun getTheGoodCall(section: Section) =
        when (section) {
            Section.Breaking,
            Section.Hot,
            Section.TopVisited -> newsRepository.getTopVisited()
            Section.Popular -> newsRepository.getPopular()
        }

    private fun enrichInformation(originalList: List<New>): Flowable<List<New>> {
        return enrichWithLogos(originalList)
    }

    private fun enrichWithLogos(originalList: List<New>): Flowable<List<New>> {
        return imagesRepository.getLogosForSources(*originalList.map { it.from }.toTypedArray())
            .subscribeOn(workerThread)
            .observeOn(workerThread)
            .toFlowable()
            .flatMap { logosMap ->
                val listToEmit = mutableListOf<New>()
                originalList.forEach {
                    listToEmit.add(it.copy(logoUrl = logosMap[it.from] ?: ""))
                }

                enrichWithPlaceholders(listToEmit)
            }
            .onErrorResumeNext(Flowable.just(originalList))
    }

    private fun enrichWithPlaceholders(originalList: List<New>): Flowable<List<New>> {
        return if (originalList.asSequence().any { it.thumb.isBlank() }) {
            imagesRepository.getPlaceholders(deviceRepository.getScreenDensity())
                .subscribeOn(workerThread)
                .observeOn(workerThread)
                .toFlowable()
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

                    Flowable.just(listToEmit.toList())
                }
                .onErrorResumeNext(Flowable.just(originalList))
        } else {
            Flowable.just(originalList)
        }
    }

    private fun normalizeCategory(category: String) = StringUtils.stripAccents(category).toLowerCase()
}
