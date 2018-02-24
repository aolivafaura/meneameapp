package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.repository.NewsRepo
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by antoniojoseoliva on 09/07/2017.
 */

class GetPopularNews @Inject constructor(val newsRepo: NewsRepo) : UseCase<List<New>, Unit>() {

    override fun buildUseCaseObservable(params: Unit): Observable<List<New>> = newsRepo.getPopular()
}
