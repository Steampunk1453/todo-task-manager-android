package org.task.manager.domain.usecase.book

import org.task.manager.domain.Result
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.repository.BookRepository
import timber.log.Timber

class GetBookshops(private val repository: BookRepository) {

    suspend fun execute(): List<Bookshop>?  {
        return when (val result =  repository.getAllBookshops()) {
            is Result.Success -> handleSuccessResult(result.data)
            is Result.Error -> result.throwable.message?.let { handleFailedResult(it) }
        }
    }

    private fun handleSuccessResult(result: List<Bookshop>): List<Bookshop> {
        Timber.d("Successful get all bookshops: %s", result)
        return result
    }

    private fun handleFailedResult(error: String): List<Bookshop> {
        Timber.e("Invalid get all bookshops: %s", error)
        return listOf()
    }

}