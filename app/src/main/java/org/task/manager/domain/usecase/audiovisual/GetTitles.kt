package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.Result
import org.task.manager.domain.model.Title
import org.task.manager.domain.repository.AudiovisualRepository
import timber.log.Timber

class GetTitles(private val repository: AudiovisualRepository) {

    suspend fun execute(type: String): List<Title>  {
        return when (val result =  repository.getAllTitles()) {
            is Result.Success -> handleSuccessResult(result.data, type)
            is Result.Error -> result.throwable.message.let { handleFailedResult(it) }
        }
    }

    private fun handleSuccessResult(result: List<Title>, type: String): List<Title> {
        Timber.d("Successful get titles: %s", result)
        return result.filter { it.type == type }
    }

    private fun handleFailedResult(error: String?): List<Title> {
        Timber.e("Invalid get titles: %s", error)
        return listOf()
    }

}