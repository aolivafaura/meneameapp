package es.mnmapp.aolv.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import es.mnmapp.aolv.data.repository.placeholders.datasource.local.PlaceholderRoom
import es.mnmapp.aolv.data.repository.placeholders.datasource.local.PlaceholdersRoomDao


@Database(entities = [(
        PlaceholderRoom::class
        )],
        version = 1,
        exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun placeholdersDao(): PlaceholdersRoomDao
}