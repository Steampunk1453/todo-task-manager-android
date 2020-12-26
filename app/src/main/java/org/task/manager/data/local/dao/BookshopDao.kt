package org.task.manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.task.manager.data.local.model.BookshopEntity

@Dao
interface BookshopDao {

    @Query("SELECT * FROM Bookshop")
    fun getAll(): List<BookshopEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(bookshops: List<BookshopEntity>)

    @Query("DELETE FROM Bookshop")
    fun deleteAll()

}