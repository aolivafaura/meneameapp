package es.mnmapp.aolv.data.entity

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

data class MeneoEntity(val id : Long,
                       val permalink : String,
                       val go : String,
                       val url : String,
                       val from : String,
                       val sub : String,
                       val status : String,
                       val user : String,
                       val clicks : Int,
                       val votes : Int,
                       val karma : Int,
                       val comments : Int,
                       val title : String,
                       val tags : String,
                       val sent_date : Long,
                       val date : Long,
                       val content : String,
                       val thumb : String)
