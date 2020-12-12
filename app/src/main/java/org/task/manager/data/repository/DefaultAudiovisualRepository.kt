package org.task.manager.data.repository

import org.task.manager.data.local.model.toDomain
import org.task.manager.data.local.model.toEntity
import org.task.manager.data.local.source.AudiovisualLocalDataSource
import org.task.manager.data.network.model.request.toRequest
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.AudiovisualRemoteDataSource
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Platform
import org.task.manager.domain.model.Title
import org.task.manager.domain.repository.AudiovisualRepository

class DefaultAudiovisualRepository(private val remoteDataSource: AudiovisualRemoteDataSource,
                                   private val localDataSource: AudiovisualLocalDataSource
) : AudiovisualRepository {

    override suspend fun save(audiovisual: Audiovisual): Audiovisual {
        val audiovisualResponse = remoteDataSource.create(audiovisual.toRequest())
        val newAudiovisual = audiovisualResponse.toDomain()
        localDataSource.save(newAudiovisual.toEntity())
        return newAudiovisual
    }

    override suspend fun update(audiovisual: Audiovisual): Audiovisual {
        val audiovisualResponse = remoteDataSource.update(audiovisual.toRequest())
        val updatedAudiovisual = audiovisualResponse.toDomain()
        localDataSource.update(updatedAudiovisual.toEntity())
        return updatedAudiovisual
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

    override suspend fun remove(id: Long) {
        remoteDataSource.delete(id)
        localDataSource.delete(id)
    }

    override suspend fun getAllTitles(): List<Title> {
        val titles = localDataSource.findAllTitles().map { it.toDomain() }
        if (titles.isEmpty()) {
            val newTitles = remoteDataSource.getAllTitles().map { it.toDomain() }
            localDataSource.saveAllTitles(newTitles.map { it.toEntity() })
            return newTitles
        }
        return titles
    }

    override suspend fun getAllPlatforms(): List<Platform> {
        val platforms = localDataSource.findAllPlatforms().map { it.toDomain() }
        if (platforms.isEmpty()) {
            val newPlatforms = remoteDataSource.getAllPlatforms().map { it.toDomain() }
            localDataSource.saveAllPlatforms(newPlatforms.map { it.toEntity() })
            return newPlatforms
        }
        return platforms
    }

}