package org.task.manager.domain.usecase

import org.task.manager.domain.model.Title
import org.task.manager.domain.repository.AudiovisualRepository

class GetTitles(private val repository: AudiovisualRepository) {

    suspend fun execute(): List<Title>  {
        return repository.getAllTitles()
    }

}