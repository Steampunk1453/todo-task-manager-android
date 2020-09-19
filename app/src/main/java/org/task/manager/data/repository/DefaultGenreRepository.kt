package org.task.manager.data.repository

import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.GenreDataSource
import org.task.manager.domain.model.Genre
import org.task.manager.domain.repository.GenreRepository

class DefaultGenreRepository(private val dataSource: GenreDataSource) : GenreRepository {

    override suspend fun getAll(): List<Genre> {
        return dataSource.getAll().map {
            it.toDomain()
        }
    }

}