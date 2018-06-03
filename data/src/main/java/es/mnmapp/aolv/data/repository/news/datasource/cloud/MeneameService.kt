package es.mnmapp.aolv.data.repository.news.datasource.cloud

import io.reactivex.Flowable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */
interface MeneameService {

    @GET("list.php")
    fun getMeneos(@QueryMap options: Map<String, String>): Flowable<MeneoApiResultDto>

    /**
     * Companion object to create the MeneameService
     */
    companion object Factory {
        fun create(httpClient: OkHttpClient, baseUrl: String): MeneameService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .baseUrl(baseUrl)
                    .build()

            return retrofit.create(MeneameService::class.java)
        }
    }
}
