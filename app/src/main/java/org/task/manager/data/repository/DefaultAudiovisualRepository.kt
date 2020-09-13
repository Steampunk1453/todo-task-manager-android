package org.task.manager.data.repository

import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.AudiovisualDataSource
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.repository.AudiovisualRepository

class DefaultAudiovisualRepository(private val dataSource: AudiovisualDataSource) : AudiovisualRepository {

    override suspend fun save(audiovisualRequest: AudiovisualRequest): Audiovisual {
       val audiovisualResponse = dataSource.create(audiovisualRequest)
       return audiovisualResponse.toDomain()
    }

    override suspend fun update(audiovisualRequest: AudiovisualRequest): Audiovisual {
        val audiovisualResponse = dataSource.update(audiovisualRequest)
        return audiovisualResponse.toDomain()
    }

    override suspend fun getAll(): List<Audiovisual> {
        return dataSource.getAll().map {
            it.toDomain()
        }
    }

    override suspend fun get(id: Long): Audiovisual {
        val audiovisualResponse = dataSource.get(id)
        return audiovisualResponse.toDomain()
    }

    override suspend fun remove(id: Long) {
        dataSource.delete(id)
    }

}