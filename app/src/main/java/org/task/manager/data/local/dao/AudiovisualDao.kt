package org.task.manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.task.manager.data.local.model.AudiovisualEntity

@Dao
interface AudiovisualDao {

    @Query("SELECT * FROM Audiovisual")
    fun getAll(): List<AudiovisualEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(audiovisuals: List<AudiovisualEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(audiovisual: AudiovisualEntity)

    @Update
    fun update(audiovisual: AudiovisualEntity)

    @Query("DELETE FROM Audiovisual")
    fun deleteAll()

    @Query("DELETE FROM Audiovisual WHERE id = :id")
    fun deleteById(id: Long)

}