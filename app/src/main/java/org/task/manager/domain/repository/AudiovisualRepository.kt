package org.task.manager.domain.repository

import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.domain.model.Audiovisual

interface AudiovisualRepository {
    suspend fun getAll(): List<Audiovisual>
    suspend fun save(audiovisualRequest: AudiovisualRequest): Audiovisual
    suspend fun remove(id: Long)
}
