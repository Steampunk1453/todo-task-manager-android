package org.task.manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.task.manager.data.local.model.TitleEntity

@Dao
interface TitleDao {

    @Query("SELECT * FROM TitleInfo")
    fun getAll(): List<TitleEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(titles: List<TitleEntity>)

    @Query("DELETE FROM TitleInfo")
    suspend fun deleteAll()

}