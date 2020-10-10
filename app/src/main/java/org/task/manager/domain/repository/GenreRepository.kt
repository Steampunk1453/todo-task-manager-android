package org.task.manager.domain.repository

import org.task.manager.domain.model.Genre

interface GenreRepository {
    suspend fun getAll(): List<Genre>
}
