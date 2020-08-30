package org.task.manager.data.repository

import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.data.network.source.AudiovisualDataSource
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.repository.AudiovisualRepository

class DefaultAudiovisualRepository(private val dataSource: AudiovisualDataSource) :
    AudiovisualRepository {

    override suspend fun getAll(): List<Audiovisual> {
        return dataSource.getAll().map {
            Audiovisual(
                it.id, it.title, it.genre, it.platform, it.platformUrl, it.startDate,
                it.deadline, it.check
            )
        }
    }

    override suspend fun save(audiovisualRequest: AudiovisualRequest): Audiovisual {
       val audiovisualResponse = dataSource.create(audiovisualRequest)
       return Audiovisual(audiovisualResponse.id, audiovisualResponse.title, audiovisualResponse.genre,
           audiovisualResponse.platform, audiovisualResponse.platformUrl,  audiovisualResponse.startDate,
           audiovisualResponse.deadline, audiovisualResponse.check)
    }

    override suspend fun remove(id: Long) {
        dataSource.delete(id)
    }

}