package es.mnmapp.aolv.meneame.di.repositoryproviders

import android.arch.persistence.room.Room
import android.content.Context
import es.mnmapp.aolv.data.database.LocalDatabase

fun createLocalDatabase(context: Context) =
        Room.databaseBuilder(context, LocalDatabase::class.java, "local").build()