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
    fun insertAll(books: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: BookEntity)

    @Update
    fun update(book: BookEntity)

    @Query("DELETE FROM Book")
    fun deleteAll()

    @Query("DELETE FROM Book WHERE id = :id")
    fun deleteById(id: Long)

}