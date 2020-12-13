package org.task.manager.data.repository

import org.task.manager.data.local.model.toDomain
import org.task.manager.data.local.model.toEntity
import org.task.manager.data.local.source.BookLocalDataSource
import org.task.manager.data.network.model.request.toRequest
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.BookRemoteDataSource
import org.task.manager.domain.Result
import org.task.manager.domain.model.Book
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.model.Editorial
import org.task.manager.domain.repository.BookRepository

class DefaultBookRepository(private val remoteDataSource: BookRemoteDataSource,
                            private val localDataSource: BookLocalDataSource
) : BookRepository {

    override suspend fun save(book: Book): Result<Book> {
        return try {
            val bookResponse = remoteDataSource.create(book.toRequest())
            val newBook = bookResponse.toDomain()
            localDataSource.save(newBook.toEntity())
            Result.Success(newBook)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun update(book: Book): Result<Book> {
        return try {
            val bookResponse = remoteDataSource.update(book.toRequest())
            val updatedBook = bookResponse.toDomain()
            localDataSource.update(updatedBook.toEntity())
            Result.Success(updatedBook)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun getAll(): Result<List<Book>> {
        return try {
            val books = localDataSource.findAll().map { it.toDomain() }
            if (books.isEmpty()) {
                val newBooks = remoteDataSource.findAll().map { it.toDomain() }
                localDataSource.saveAll(newBooks.map { it.toEntity() })
                return Result.Success(newBooks)
            }
            return Result.Success(books)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }

    }

    override suspend fun remove(id: Long) {
        remoteDataSource.delete(id)
        localDataSource.delete(id)
    }

    override suspend fun getAllEditorials(): Result<List<Editorial>> {
        return try {
            val editorials = localDataSource.findAllEditorials().map { it.toDomain() }
            if (editorials.isEmpty()) {
                val newEditorials = remoteDataSource.findAllEditorials().map { it.toDomain() }
                localDataSource.saveAllEditorials(newEditorials.map { it.toEntity() })
                return Result.Success(newEditorials)
            }
            return Result.Success(editorials)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }

    }

    override suspend fun getAllBookshops(): Result<List<Bookshop>> {
        return try {
            val bookshops = localDataSource.findAllBookshops().map { it.toDomain() }
            if (bookshops.isEmpty()) {
                val newBookshops = remoteDataSource.findAllBookshops().map { it.toDomain() }
                localDataSource.saveAllBookshops(newBookshops.map { it.toEntity() })
                return Result.Success(newBookshops)
            }
            return Result.Success(bookshops)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }

    }

}