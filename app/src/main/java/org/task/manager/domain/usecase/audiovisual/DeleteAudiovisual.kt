package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.repository.AudiovisualRepository

class DeleteAudiovisual(private val repository: AudiovisualRepository) {

    suspend fun execute(id: Long) {
       return repository.remove(id)
    }

}