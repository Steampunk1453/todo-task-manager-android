package org.task.manager.data.repository

import org.task.manager.data.local.model.toDomain
import org.task.manager.data.local.model.toEntity
import org.task.manager.data.local.source.BookLocalDataSource
import org.task.manager.data.network.model.request.toRequest
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.BookRemoteDataSource
import org.task.manager.domain.model.Book
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.model.Editorial
import org.task.manager.domain.repository.BookRepository

class DefaultBookRepository(private val remoteDataSource: BookRemoteDataSource,
                            private val localDataSource: BookLocalDataSource
) : BookRepository {

    override suspend fun save(book: Book): Book {
        val bookResponse = remoteDataSource.create(book.toRequest())
        val newBook = bookResponse.toDomain()
        localDataSource.save(newBook.toEntity())
        return book
    }

    override suspend fun update(book: Book): Book {
        val bookResponse = remoteDataSource.update(book.toRequest())
        val updatedBook = bookResponse.toDomain()
        localDataSource.update(updatedBook.toEntity())
        return updatedBook
    }

    override suspend fun getAll(): List<Book> {
        val books = localDataSource.findAll().map { it.toDomain() }
        if (books.isEmpty()) {
            val newBooks = remoteDataSource.findAll().map { it.toDomain() }
            localDataSource.saveAll(newBooks.map { it.toEntity() })
            return newBooks
        }
        return books
    }

    override suspend fun remove(id: Long) {
        remoteDataSource.delete(id)
        localDataSource.delete(id)
    }

    override suspend fun getAllEditorials(): List<Editorial> {
        val editorials = localDataSource.findAllEditorials().map { it.toDomain() }
        if (editorials.isEmpty()) {
            val newEditorials = remoteDataSource.findAllEditorials().map { it.toDomain() }
            localDataSource.saveAllEditorials(newEditorials.map { it.toEntity() })
            return newEditorials
        }
        return editorials
    }

    override suspend fun getAllBookshops(): List<Bookshop> {
        val bookshops = localDataSource.findAllBookshops().map { it.toDomain() }
        if (bookshops.isEmpty()) {
            val newBookshops = remoteDataSource.findAllBookshops().map { it.toDomain() }
            localDataSource.saveAllBookshops(newBookshops.map { it.toEntity() })
            return newBookshops
        }
        return bookshops
    }

}