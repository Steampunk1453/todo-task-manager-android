package org.task.manager.data.local.source

import org.task.manager.data.local.dao.BookDao
import org.task.manager.data.local.model.BookEntity

class BookLocalDataSource(private val dao: BookDao) {

    fun findAll(): List<BookEntity> = dao.getAll()

    suspend fun saveAll(books: List<BookEntity>) = dao.insertAll(books)

    suspend fun save(book: BookEntity) = dao.insert(book)

    suspend fun update(book: BookEntity) = dao.update(book)

    suspend fun delete(id: Long) = dao.deleteById(id)

}