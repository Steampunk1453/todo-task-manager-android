package org.task.manager.domain.usecase.shared

import org.task.manager.domain.Result
import org.task.manager.domain.model.Genre
import org.task.manager.domain.repository.GenreRepository
import timber.log.Timber

class GetGenres(private val repository: GenreRepository) {

    suspend fun execute(): List<Genre> {
        return when (val result = repository.getAll()) {
            is Result.Success -> handleSuccessResult(result.data)
            is Result.Error -> result.throwable.message.let { handleFailedResult(it) }
        }
    }

    private fun handleSuccessResult(result: List<Genre>): List<Genre> {
        Timber.d("Successful get genres: %s", result)
        return result
    }

    private fun handleFailedResult(error: String?): List<Genre> {
        Timber.e("Invalid get genres: %s", error)
        return listOf()
    }

}