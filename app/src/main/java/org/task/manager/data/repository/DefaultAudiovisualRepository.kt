package org.task.manager.data.repository

import org.task.manager.data.local.model.toDomain
import org.task.manager.data.local.model.toEntity
import org.task.manager.data.local.source.AudiovisualLocalDataSource
import org.task.manager.data.network.model.request.toRequest
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.AudiovisualRemoteDataSource
import org.task.manager.domain.Result
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Platform
import org.task.manager.domain.model.Title
import org.task.manager.domain.repository.AudiovisualRepository

class DefaultAudiovisualRepository(private val remoteDataSource: AudiovisualRemoteDataSource,
                                   private val localDataSource: AudiovisualLocalDataSource
) : AudiovisualRepository {

    override suspend fun save(audiovisual: Audiovisual): Result<Audiovisual> {
        return try {
            val audiovisualResponse = remoteDataSource.create(audiovisual.toRequest())
            val newAudiovisual = audiovisualResponse.toDomain()
            localDataSource.save(newAudiovisual.toEntity())
            Result.Success(newAudiovisual)
        }  catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun update(audiovisual: Audiovisual): Result<Audiovisual> {
        return try {
            val audiovisualResponse = remoteDataSource.update(audiovisual.toRequest())
            val updatedAudiovisual = audiovisualResponse.toDomain()
            localDataSource.update(updatedAudiovisual.toEntity())
            Result.Success(updatedAudiovisual)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun getAll(): Result<List<Audiovisual>> {
        return try {
            val audiovisuals = localDataSource.findAll().map { it.toDomain() }
            if (audiovisuals.isEmpty()) {
                val newAudiovisuals = remoteDataSource.findAll().map { it.toDomain() }
                localDataSource.saveAll(newAudiovisuals.map { it.toEntity() })
                return Result.Success(newAudiovisuals)
            }
            return Result.Success(audiovisuals)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun remove(id: Long) {
        remoteDataSource.delete(id)
        localDataSource.delete(id)
    }

    override suspend fun getAllTitles(): Result<List<Title>> {
        return try {
            val titles = localDataSource.findAllTitles().map { it.toDomain() }
            if (titles.isEmpty()) {
                val newTitles = remoteDataSource.getAllTitles().map { it.toDomain() }
                localDataSource.saveAllTitles(newTitles.map { it.toEntity() })
                return Result.Success(newTitles)
            }
            return Result.Success(titles)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun getAllPlatforms(): Result<List<Platform>> {
        return try {
            val platforms = localDataSource.findAllPlatforms().map { it.toDomain() }
            if (platforms.isEmpty()) {
                val newPlatforms = remoteDataSource.getAllPlatforms().map { it.toDomain() }
                localDataSource.saveAllPlatforms(newPlatforms.map { it.toEntity() })
                return  Result.Success(newPlatforms)
            }
            return Result.Success(platforms)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

}