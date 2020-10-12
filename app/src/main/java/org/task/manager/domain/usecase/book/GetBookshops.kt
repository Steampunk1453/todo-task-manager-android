package org.task.manager.domain.usecase.book

import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.repository.BookRepository

class GetBookshops(private val repository: BookRepository) {

    suspend fun execute(): List<Bookshop>  {
        return repository.getAllBookshops()
    }

}