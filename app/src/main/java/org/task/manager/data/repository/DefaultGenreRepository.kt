package org.task.manager.data.repository

import org.task.manager.data.local.model.toDomain
import org.task.manager.data.local.model.toEntity
import org.task.manager.data.local.source.GenreLocalDataSource
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.GenreRemoteDataSource
import org.task.manager.domain.model.Genre
import org.task.manager.domain.repository.GenreRepository

class DefaultGenreRepository(private val remoteDataSource: GenreRemoteDataSource,
                             private val localDataSource: GenreLocalDataSource
) : GenreRepository {

    override suspend fun getAll(): List<Genre> {
        val genres = localDataSource.findAll().map { it.toDomain() }
        if (genres.isEmpty()) {
            val newGenres = remoteDataSource.getAll().map { it.toDomain() }
            localDataSource.saveAll(newGenres.map { it.toEntity() })
            return newGenres
        }
        return genres
    }

} 