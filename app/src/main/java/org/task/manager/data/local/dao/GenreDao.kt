package org.task.manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.task.manager.data.local.model.GenreEntity

@Dao
interface GenreDao {

    @Query("SELECT * FROM Genre")
    fun getAll(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(genres: List<GenreEntity>)

    @Query("DELETE FROM Genre")
    suspend fun deleteAll()

}