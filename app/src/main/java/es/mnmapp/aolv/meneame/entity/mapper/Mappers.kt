package es.mnmapp.aolv.meneame.entity.mapper

import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.meneame.entity.NewCellUi

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

fun fromNewToNewCellUi(new: New) = NewCellUi(
        new.id,
        new.url,
        new.title,
        new.thumb,
        new.positiveVotes,
        new.negativeVotes,
        new.karma,
        new.comments,
        new.date
)
