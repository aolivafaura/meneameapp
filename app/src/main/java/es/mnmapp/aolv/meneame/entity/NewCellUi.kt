package es.mnmapp.aolv.meneame.entity

import es.mnmapp.aolv.domain.entity.New

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
        val date: Long,
        val logoUrl: String
)

fun fromNewToNewCellUi(new: New) = NewCellUi(
        new.id,
        new.url,
        new.title,
        new.from,
        new.thumb,
        new.positiveVotes,
        new.negativeVotes,
        new.karma,
        new.comments,
        new.date,
        new.logoUrl ?: ""
)