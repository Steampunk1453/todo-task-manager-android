package org.task.manager.data.repository

import org.task.manager.data.local.model.toDomain
import org.task.manager.data.local.model.toEntity
import org.task.manager.data.local.source.AudiovisualLocalDataSource
import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.AudiovisualRemoteDataSource
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Platform
import org.task.manager.domain.model.Title
import org.task.manager.domain.repository.AudiovisualRepository

class DefaultAudiovisualRepository(private val remoteDataSource: AudiovisualRemoteDataSource,
                                   private val localDataSource: AudiovisualLocalDataSource
) : AudiovisualRepository {

    override suspend fun save(audiovisualRequest: AudiovisualRequest): Audiovisual {
        val audiovisualResponse = remoteDataSource.create(audiovisualRequest)
        val audiovisual = audiovisualResponse.toDomain()
        localDataSource.save(audiovisual.toEntity())
        return audiovisual
    }

    override suspend fun update(audiovisualRequest: AudiovisualRequest): Audiovisual {
        val audiovisualResponse = remoteDataSource.update(audiovisualRequest)
        val audiovisual = audiovisualResponse.toDomain()
        localDataSource.update(audiovisual.toEntity())
        return audiovisual
    }

    override suspend fun getAll(): List<Audiovisual> {
        val audiovisuals = localDataSource.findAll().map { it.toDomain() }
        if (audiovisuals.isEmpty()) {
            val newAudiovisuals = remoteDataSource.findAll().map { it.toDomain() }
            localDataSource.saveAll(newAudiovisuals.map { it.toEntity() })
            return newAudiovisuals
        }
       return audiovisuals
    }

    override suspend fun get(id: Long): Audiovisual {
        val audiovisualResponse = remoteDataSource.findById(id)
        return audiovisualResponse.toDomain()
    }

    override suspend fun remove(id: Long) {
        remoteDataSource.delete(id)
        localDataSource.delete(id)
    }

    override suspend fun getAllTitles(): List<Title> {
        return remoteDataSource.getAllTitles().map { it.toDomain() }
    }

    override suspend fun getAllPlatforms(): List<Platform> {
        return remoteDataSource.getAllPlatforms().map { it.toDomain() }
    }

}