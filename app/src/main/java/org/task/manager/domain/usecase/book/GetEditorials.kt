package org.task.manager.domain.usecase.book

import org.task.manager.domain.model.Editorial
import org.task.manager.domain.repository.BookRepository

class GetEditorials(private val repository: BookRepository) {

    suspend fun execute(): List<Editorial>  {
        return repository.getAllEditorials()
    }

}