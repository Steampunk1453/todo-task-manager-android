package org.task.manager.domain.usecase.shared

import org.task.manager.domain.model.Genre
import org.task.manager.domain.repository.GenreRepository

class GetGenres(private val repository: GenreRepository) {

    suspend fun execute(): List<Genre>  {
        return repository.getAll()
    }

}