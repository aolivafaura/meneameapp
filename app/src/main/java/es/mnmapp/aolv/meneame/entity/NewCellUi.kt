package es.mnmapp.aolv.meneame.entity

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

data class NewCellUi(
        val id: Long,
        val url: String,
        val title: String,
        val source: String,
        val thumb: String,
        val positiveVotes: Int,
        val negativeVotes: Int,
        val karma: Int,
        val comments: Int,
        val date: Long
)
