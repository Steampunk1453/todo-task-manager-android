package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.repository.AudiovisualRepository

class GetAudiovisuals(private val repository: AudiovisualRepository) {

    suspend fun execute(): List<Audiovisual>  {
        return repository.getAll()
    }

}