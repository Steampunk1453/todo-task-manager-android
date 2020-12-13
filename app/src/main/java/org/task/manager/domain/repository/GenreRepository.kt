package org.task.manager.domain.repository

import org.task.manager.domain.Result
import org.task.manager.domain.model.Genre

interface GenreRepository {
    suspend fun getAll(): Result<List<Genre>>
}
