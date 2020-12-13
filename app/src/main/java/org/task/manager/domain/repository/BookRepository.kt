package org.task.manager.domain.repository

import org.task.manager.domain.Result
import org.task.manager.domain.model.Book
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.model.Editorial

interface BookRepository {
    suspend fun save(book: Book): Result<Book>
    suspend fun update(book: Book): Result<Book>
    suspend fun getAll():  Result<List<Book>>
    suspend fun remove(id: Long)
    suspend fun getAllEditorials(): Result<List<Editorial>>
    suspend fun getAllBookshops(): Result<List<Bookshop>>
}
