package org.task.manager.data.repository

import org.task.manager.data.network.model.request.BookRequest
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.BookDataSource
import org.task.manager.domain.model.Book
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.model.Editorial
import org.task.manager.domain.repository.BookRepository

class DefaultBookRepository(private val dataSource: BookDataSource) : BookRepository {
    override suspend fun save(bookRequest: BookRequest): Book {
        val bookResponse = dataSource.create(bookRequest)
        return bookResponse.toDomain()
    }

    override suspend fun update(bookRequest: BookRequest): Book {
        val bookResponse = dataSource.update(bookRequest)
        return bookResponse.toDomain()
    }

    override suspend fun getAll(): List<Book> {
        return dataSource.getAll().map {
            it.toDomain()
        }
    }

    override suspend fun get(id: Long): Book {
        val bookResponse = dataSource.get(id)
        return bookResponse.toDomain()
    }

    override suspend fun remove(id: Long) {
        dataSource.delete(id)
    }

    override suspend fun getAllBookshops(): List<Bookshop> {
        return dataSource.getAllBookshops().map {
            it.toDomain()
        }
    }

    override suspend fun getAllEditorials(): List<Editorial> {
        return dataSource.getAllEditorials().map {
            it.toDomain()
        }
    }

}