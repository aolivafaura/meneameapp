package es.mnmapp.aolv.domain.entity

/**
 * Created by antoniojoseoliva on 19/07/2017.
 */

data class New(val id: Long,
               val url: String,
               val title: String,
               val category: String,
               val thumb: String,
               val from: String,
               val positiveVotes: Int,
               val negativeVotes: Int,
               val karma: Int,
               val comments: Int,
               val sendDate: Long,
               val date: Long,
               val tags: String,
               var logoUrl: String? = "")
