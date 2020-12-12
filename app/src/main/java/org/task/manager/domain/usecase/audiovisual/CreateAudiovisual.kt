package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.Result
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.repository.AudiovisualRepository
import timber.log.Timber

class CreateAudiovisual(private val repository: AudiovisualRepository) {

    suspend fun execute(audiovisual: Audiovisual): Audiovisual? {
       return when (val result = repository.save(audiovisual)) {
           is Result.Success -> handleSuccessResult(result.data)
           is Result.Error -> result.throwable.message?.let { handleFailedResult(it) }
       }
    }

    private fun handleSuccessResult(result: Audiovisual): Audiovisual {
        Timber.d("Successful audiovisual creation: %s", result)
        return result
    }

    private fun handleFailedResult(error: String): Audiovisual? {
        Timber.e("Invalid audiovisual creation: %s", error)
        return null
    }

}