package es.mnmapp.aolv.data.repository.cloud

import es.mnmapp.aolv.data.entity.mapper.fromMeneoEntityListToMeneoList
import es.mnmapp.aolv.data.net.MeneameService
import es.mnmapp.aolv.domain.entity.Meneo
import es.mnmapp.aolv.domain.repository.MeneosRepo
import io.reactivex.Observable

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */

class MeneosCloudRepo(val meneameService : MeneameService) : MeneosRepo {

    override fun getPopular() : Observable<List<Meneo>> {

        val options = HashMap<String, String>().apply { put("popular", "true") }

        return meneameService.getMeneos(options).map { fromMeneoEntityListToMeneoList(it.objects) }
    }

    override fun getTopVisited() : Observable<List<Meneo>> {

        val options = HashMap<String, String>().apply { put("top_visited", "true") }

        return meneameService.getMeneos(options).map { fromMeneoEntityListToMeneoList(it.objects) }
    }
}
