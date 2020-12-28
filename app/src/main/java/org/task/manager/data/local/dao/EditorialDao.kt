package org.task.manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.task.manager.data.local.model.EditorialEntity

@Dao
interface EditorialDao {

    @Query("SELECT * FROM Editorial")
    fun getAll(): List<EditorialEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(editorials: List<EditorialEntity>)

    @Query("DELETE FROM Editorial")
    fun deleteAll()

}