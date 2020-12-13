package org.task.manager.domain.usecase.book

import org.task.manager.domain.Result
import org.task.manager.domain.model.Book
import org.task.manager.domain.repository.BookRepository
import timber.log.Timber

class CreateBook(private val repository: BookRepository) {

    suspend fun execute(book: Book): Book? {
        return when (val result = repository.save(book)) {
            is Result.Success -> handleSuccessResult(result.data)
            is Result.Error -> result.throwable.message?.let { handleFailedResult(it) }
        }
    }

    private fun handleSuccessResult(result: Book): Book {
        Timber.d("Successful book creation: %s", result)
        return result
    }

    private fun handleFailedResult(error: String): Book? {
        Timber.e("Invalid book creation: %s", error)
        return null
    }

}