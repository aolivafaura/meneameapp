package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.repository.NewsRepo
import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Created by antoniojoseoliva on 09/07/2017.
 */

class GetPopularNews(postExecutionThread: Scheduler, workerThread: Scheduler, private val newsRepo: NewsRepo)
    : UseCase<List<New>, Unit>(postExecutionThread, workerThread) {

    override fun buildUseCaseObservable(params: Unit): Observable<List<New>> = newsRepo.getPopular()
}
