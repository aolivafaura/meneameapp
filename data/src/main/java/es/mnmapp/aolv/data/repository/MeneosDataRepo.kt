package es.mnmapp.aolv.data.repository

import es.mnmapp.aolv.data.net.MeneameService
import es.mnmapp.aolv.data.repository.cloud.MeneosCloudRepo
import es.mnmapp.aolv.domain.entity.Meneo
import es.mnmapp.aolv.domain.repository.MeneosRepo
import io.reactivex.Observable

/**
 * This class should decide between available data sources
 * For the moment, we have just cloud data source, so it's an easy choice
 * Created by antoniojoseoliva on 08/07/2017.
 */
class MeneosDataRepo(private val meneameService: MeneameService) : MeneosRepo {

    override fun getPopular(): Observable<List<Meneo>> {
        return MeneosCloudRepo(meneameService).getPopular()
    }

    override fun getTopVisited(): Observable<List<Meneo>> {
        return MeneosCloudRepo(meneameService).getTopVisited()
    }
}
