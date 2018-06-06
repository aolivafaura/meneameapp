package es.mnmapp.aolv.meneame.di.repositoryproviders

import android.arch.persistence.room.Room
import android.content.Context
import es.mnmapp.aolv.data.database.LocalDatabase

const val LOCAL_DATABASE_NAME = "local"

fun createLocalDatabase(context: Context): LocalDatabase =
        Room.databaseBuilder(context, LocalDatabase::class.java, LOCAL_DATABASE_NAME).build()