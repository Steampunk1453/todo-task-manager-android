package org.task.manager.domain.usecase.book

import org.task.manager.domain.Result
import org.task.manager.domain.model.Book
import org.task.manager.domain.repository.BookRepository
import timber.log.Timber

class GetBooks(private val repository: BookRepository) {

    suspend fun execute(): List<Book>  {
        return when (val result = repository.getAll()) {
            is Result.Success -> handleSuccessResult(result.data)
            is Result.Error -> result.throwable.message.let { handleFailedResult(it) }
        }
    }

    private fun handleSuccessResult(result: List<Book>): List<Book> {
        Timber.d("Successful get all books: %s", result)
        return result
    }

    private fun handleFailedResult(error: String?): List<Book> {
        Timber.e("Invalid get all books: %s", error)
        return listOf()
    }

}