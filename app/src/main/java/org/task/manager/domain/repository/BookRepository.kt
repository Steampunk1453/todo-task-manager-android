package org.task.manager.domain.repository

import org.task.manager.data.network.model.request.BookRequest
import org.task.manager.domain.model.Book
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.model.Editorial

interface BookRepository {
    suspend fun save(bookRequest: BookRequest): Book
    suspend fun update(bookRequest: BookRequest): Book
    suspend fun getAll(): List<Book>
    suspend fun get(id: Long): Book
    suspend fun remove(id: Long)
    suspend fun getAllEditorials(): List<Editorial>
    suspend fun getAllBookshops(): List<Bookshop>
}
