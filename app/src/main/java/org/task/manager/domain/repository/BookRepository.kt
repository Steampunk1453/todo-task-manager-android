package org.task.manager.domain.repository

import org.task.manager.domain.model.Book
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.model.Editorial

interface BookRepository {
    suspend fun save(book: Book): Book
    suspend fun update(book: Book): Book
    suspend fun getAll(): List<Book>
    suspend fun remove(id: Long)
    suspend fun getAllEditorials(): List<Editorial>
    suspend fun getAllBookshops(): List<Bookshop>
}
