package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.Result
import org.task.manager.domain.model.Platform
import org.task.manager.domain.repository.AudiovisualRepository
import timber.log.Timber

class GetPlatforms(private val repository: AudiovisualRepository) {

    suspend fun execute(): List<Platform>?  {
        return when (val result = repository.getAllPlatforms()) {
            is Result.Success -> handleSuccessResult(result.data)
            is Result.Error -> result.throwable.message?.let { handleFailedResult(it) }
        }
    }

    private fun handleSuccessResult(result: List<Platform>): List<Platform> {
        Timber.d("Successful get platforms: %s", result)
        return result
    }

    private fun handleFailedResult(error: String): List<Platform> {
        Timber.e("Invalid get platforms: %s", error)
        return listOf()
    }

}