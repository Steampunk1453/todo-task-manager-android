package org.task.manager.data.repository

import org.task.manager.data.network.source.AudiovisualDataSource
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.repository.AudiovisualRepository

class DefaultAudiovisualRepository(private val dataSource: AudiovisualDataSource) :
    AudiovisualRepository {

    override suspend fun getAll(): List<Audiovisual> =
        dataSource.getAll().map {
            Audiovisual(
                it.id, it.title, it.genre, it.platform, it.platformUrl, it.startDate,
                it.deadline, it.check
            )
        }

    override suspend fun save(audiovisual: Audiovisual): Audiovisual {
        TODO("Not yet implemented")
    }

}