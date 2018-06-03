package es.mnmapp.aolv.data.repository.placeholders.datasource.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface PlaceholdersRoomDao {

    @Query("SELECT * FROM placeholder")
    fun getAll(): Single<List<PlaceholderRoom>>

    @Insert
    fun insertAll(vararg placeholderRoom: PlaceholderRoom)
}