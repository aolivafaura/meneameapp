package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.entity.Section
import es.mnmapp.aolv.domain.repository.NewsRepository
import es.mnmapp.aolv.domain.repository.PlaceholdersRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Scheduler
import io.reactivex.internal.operators.flowable.FlowableCreate
import org.apache.commons.lang3.StringUtils

/**
 * Created by antoniojoseoliva on 09/07/2017.
 */

class GetNews(
        private val postExecutionThread: Scheduler,
        private val workerThread: Scheduler,
        private val newsRepository: NewsRepository,
        private val placeholdersRepository: PlaceholdersRepository
) : UseCase<List<New>, Section>(postExecutionThread, workerThread) {

    override fun buildUseCaseObservable(params: Section): Flowable<List<New>> {
        return FlowableCreate({ emitter ->
            getTheGoodCall(params).subscribe(
                    {
                        emitter.onNext(it)
                        enrichInformation(it, emitter)
                    },
                    {
                        emitter.tryOnError(it)
                    }
            )
        }, BackpressureStrategy.LATEST)
    }

    private fun getTheGoodCall(section: Section) =
            when (section) {
                Section.Breaking,
                Section.Hot,
                Section.TopVisited -> newsRepository.getTopVisited()
                Section.Popular -> newsRepository.getPopular()
            }

    private fun enrichInformation(originalList: List<New>, emitter: FlowableEmitter<List<New>>) {
        enrichWithLogos(originalList, emitter)
    }

    private fun enrichWithLogos(originalList: List<New>, emitter: FlowableEmitter<List<New>>) {
        placeholdersRepository.getLogosForSources(*originalList.map { it.from }.toTypedArray())
                .subscribeOn(workerThread)
                .observeOn(postExecutionThread)
                .subscribe { logosMap, _ ->
                    val listToEmit = mutableListOf<New>()
                    originalList.forEach {
                        listToEmit.add(it.copy(logoUrl = logosMap[it.from] ?: ""))
                    }

                    enrichWithPlaceholders(listToEmit, emitter)
                }
    }

    private fun enrichWithPlaceholders(originalList: List<New>, emitter: FlowableEmitter<List<New>>) {
        if (originalList.any { it.thumb.isBlank() }) {
            placeholdersRepository.getPlaceholders()
                    .subscribeOn(workerThread)
                    .observeOn(postExecutionThread)
                    .subscribe { list, _ ->
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

                        emitter.onNext(listToEmit)
                    }
        } else {
            emitter.onNext(originalList)
        }
    }

    private fun normalizeCategory(category: String) = StringUtils.stripAccents(category).toLowerCase()
}
