package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.entity.New
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

class GetPopularNews(
        private val postExecutionThread: Scheduler,
        private val workerThread: Scheduler,
        private val newsRepository: NewsRepository,
        private val placeholdersRepository: PlaceholdersRepository
) : UseCase<List<New>, Unit>(postExecutionThread, workerThread) {

    override fun buildUseCaseObservable(params: Unit): Flowable<List<New>> {
        return FlowableCreate({ emitter ->
            newsRepository.getPopular().subscribe(
                    {
                        emitter.onNext(it)
                        checkForNewsWithoutImage(it, emitter)
                    },
                    {

                    }
            )
        }, BackpressureStrategy.LATEST)
    }

    private fun checkForNewsWithoutImage(originalList: List<New>, emitter: FlowableEmitter<List<New>>) {
        if (originalList.any { it.thumb.isBlank() } ) {
            placeholdersRepository.getPlaceholders()
                    .subscribeOn(workerThread)
                    .observeOn(postExecutionThread)
                    .subscribe { list, _ ->
                        val categoriesMap = list.map { it.category to it }.toMap()
                        val emittedList = mutableListOf<New>()
                        originalList.forEach {
                            val imageUrl = if (it.thumb.isBlank()) {
                                categoriesMap[normalizeCategory(it.category)]?.url ?: ""
                            } else {
                                it.thumb
                            }

                            emittedList.add(it.copy(thumb = imageUrl))
                        }

                        emitter.onNext(emittedList)
                    }
        }
    }

    private fun normalizeCategory(category: String) = StringUtils.stripAccents(category).toLowerCase()
}
