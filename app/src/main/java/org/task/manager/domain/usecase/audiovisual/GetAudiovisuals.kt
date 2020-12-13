package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.Result
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.repository.AudiovisualRepository
import timber.log.Timber

class GetAudiovisuals(private val repository: AudiovisualRepository) {

    suspend fun execute(): List<Audiovisual>?  {
        return when (val result = repository.getAll()) {
            is Result.Success -> handleSuccessResult(result.data)
            is Result.Error -> result.throwable.message?.let { handleFailedResult(it) }
        }
    }

    private fun handleSuccessResult(result: List<Audiovisual>): List<Audiovisual> {
        Timber.d("Successful get all audiovisuals: %s", result)
        return result
    }

    private fun handleFailedResult(error: String): List<Audiovisual> {
        Timber.e("Invalid get all audiovisuals: %s", error)
        return listOf()
    }

}