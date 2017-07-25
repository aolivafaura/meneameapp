package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.entity.Meneo
import es.mnmapp.aolv.domain.repository.MeneosRepo
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by antoniojoseoliva on 09/07/2017.
 */

class GetPopularMeneos @Inject constructor(val meneosRepo : MeneosRepo,
                                           postExecutionThread : Scheduler) : UseCase<List<Meneo>, Unit>(postExecutionThread) {

    override fun buildUseCaseObservable(params : Unit) : Observable<List<Meneo>> {

        return meneosRepo.getPopular()
    }
}
