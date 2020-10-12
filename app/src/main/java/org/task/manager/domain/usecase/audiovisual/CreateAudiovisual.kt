package org.task.manager.domain.usecase.audiovisual

import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.repository.AudiovisualRepository

class CreateAudiovisual(private val repository: AudiovisualRepository) {

    suspend fun execute(audiovisualRequest: AudiovisualRequest): Audiovisual {
       return repository.save(audiovisualRequest)
    }

}