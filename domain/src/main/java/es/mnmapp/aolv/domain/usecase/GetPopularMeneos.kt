package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.entity.Meneo
import es.mnmapp.aolv.domain.repository.MeneosRepo
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by antoniojoseoliva on 09/07/2017.
 */

class GetPopularMeneos @Inject constructor(val meneosRepo : MeneosRepo) : UseCase<List<Meneo>, Unit>() {

    override fun buildUseCaseObservable(params : Unit) : Observable<List<Meneo>> = meneosRepo.getPopular()
}
