package org.task.manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.task.manager.data.local.model.BookEntity

@Dao
interface BookDao {

    @Query("SELECT * FROM Book")
    fun getAll(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(books: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Update
    suspend fun update(book: BookEntity)

    @Query("DELETE FROM Book")
    suspend fun deleteAll()

    @Query("DELETE FROM Book WHERE id = :id")
    suspend fun deleteById(id: Long)

}