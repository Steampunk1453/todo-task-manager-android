package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.model.Platform
import org.task.manager.domain.repository.AudiovisualRepository

class GetPlatforms(private val repository: AudiovisualRepository) {

    suspend fun execute(): List<Platform>  {
        return repository.getAllPlatforms()
    }

}