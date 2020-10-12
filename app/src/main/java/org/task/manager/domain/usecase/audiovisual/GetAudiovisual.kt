package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.repository.AudiovisualRepository

class GetAudiovisual(private val repository: AudiovisualRepository) {

    suspend fun execute(id: Long): Audiovisual {
       return repository.get(id)
    }

}