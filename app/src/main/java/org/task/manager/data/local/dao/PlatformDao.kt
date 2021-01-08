package org.task.manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.task.manager.data.local.model.PlatformEntity

@Dao
interface PlatformDao {

    @Query("SELECT * FROM Platform")
    fun getAll(): List<PlatformEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(platforms: List<PlatformEntity>)

    @Query("DELETE FROM Platform")
    suspend fun deleteAll()

}