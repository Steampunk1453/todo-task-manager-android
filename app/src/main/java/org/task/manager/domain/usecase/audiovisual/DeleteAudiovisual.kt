package org.task.manager.domain.usecase.audiovisual

import org.task.manager.domain.model.state.DeleteState
import org.task.manager.domain.repository.AudiovisualRepository

class DeleteAudiovisual(private val repository: AudiovisualRepository) {

    suspend fun execute(id: Long): DeleteState {
        repository.remove(id)
        return DeleteState.DELETE_COMPLETED
    }

}