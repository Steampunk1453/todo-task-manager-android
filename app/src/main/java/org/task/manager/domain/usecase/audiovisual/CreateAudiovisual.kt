package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.repository.AudiovisualRepository

class CreateAudiovisual(private val repository: AudiovisualRepository) {

    suspend fun execute(audiovisual: Audiovisual): Audiovisual {
       return repository.save(audiovisual)
    }

}