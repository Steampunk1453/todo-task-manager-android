package org.task.manager.domain.usecase.book

import org.task.manager.domain.Result
import org.task.manager.domain.model.Book
import org.task.manager.domain.repository.BookRepository
import timber.log.Timber

class UpdateBook(private val repository: BookRepository) {

    suspend fun execute(book: Book): Book? {
        return when (val result = repository.update(book)) {
            is Result.Success -> handleSuccessResult(result.data)
            is Result.Error -> result.throwable.message?.let { handleFailedResult(it) }
        }
    }

    private fun handleSuccessResult(result: Book): Book {
        Timber.d("Successful book update: %s", result)
        return result
    }

    private fun handleFailedResult(error: String): Book? {
        Timber.e("Invalid book update: %s", error)
        return null
    }

}