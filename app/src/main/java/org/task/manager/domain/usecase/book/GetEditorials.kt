package org.task.manager.domain.usecase.book

import org.task.manager.domain.Result
import org.task.manager.domain.model.Editorial
import org.task.manager.domain.repository.BookRepository
import timber.log.Timber

class GetEditorials(private val repository: BookRepository) {

    suspend fun execute(): List<Editorial>  {
        return when (val result = repository.getAllEditorials()) {
            is Result.Success -> handleSuccessResult(result.data)
            is Result.Error -> result.throwable.message.let { handleFailedResult(it) }
        }
    }

    private fun handleSuccessResult(result: List<Editorial>): List<Editorial> {
        Timber.d("Successful get all editorials: %s", result)
        return result
    }

    private fun handleFailedResult(error: String?): List<Editorial> {
        Timber.e("Invalid get all editorials: %s", error)
        return listOf()
    }


}