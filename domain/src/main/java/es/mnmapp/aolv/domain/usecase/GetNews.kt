/*
 *     Copyright 2018 @ https://github.com/aolivafaura/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.UI_THREAD
import es.mnmapp.aolv.domain.WORKER_THREAD
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.entity.Section
import es.mnmapp.aolv.domain.extensions.stripAccents
import es.mnmapp.aolv.domain.repository.DeviceRepository
import es.mnmapp.aolv.domain.repository.ImagesRepository
import es.mnmapp.aolv.domain.repository.NewsRepository
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by antoniojoseoliva on 09/07/2017.
 */

class GetNews @Inject constructor(
    @Named(UI_THREAD) postExecutionThread: Scheduler,
    @Named(WORKER_THREAD) workerThread: Scheduler,
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

    private fun enrichInformation(originalList: List<New>): Flowable<List<New>> =
        enrichWithLogos(originalList)
            .compose(applySingleWorkerSchedulers())
            .flatMap {
                if (hasAnyEmptyImage(it)) {
                    enrichWithPlaceholders(it)
                } else {
                    Single.just(it)
                }
            }
            .toFlowable()

    private fun enrichWithLogos(originalList: List<New>): Single<List<New>> =
        imagesRepository.getLogosForSources(*originalList.map { it.from }.toTypedArray())
            .onErrorReturn { emptyMap() }
            .flatMap { logosMap ->
                val listToEmit = mutableListOf<New>()

                originalList.forEach {
                    listToEmit.add(it.copy(logoUrl = logosMap[it.from] ?: ""))
                }

                Single.just(listToEmit)
            }

    private fun enrichWithPlaceholders(originalList: List<New>): Single<List<New>> =
        imagesRepository.getPlaceholders(deviceRepository.getScreenDensity())
            .onErrorReturn { emptyList() }
            .flatMap { list ->
                val categoriesMap = list.map { it.category to it }.toMap()
                val listToEmit = mutableListOf<New>()

                originalList.forEach {
                    val imageUrl = if (it.thumb.isBlank()) {
                        categoriesMap[it.category.stripAccents().toLowerCase()]?.url ?: ""
                    } else {
                        it.thumb
                    }
                    listToEmit.add(it.copy(thumb = imageUrl))
                }

                Single.just(listToEmit.toList())
            }

    private fun hasAnyEmptyImage(list: List<New>): Boolean = list.any { it.thumb.isBlank() }
}
