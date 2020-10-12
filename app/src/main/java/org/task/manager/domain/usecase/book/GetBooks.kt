package org.task.manager.domain.usecase.book

import org.task.manager.domain.model.Book
import org.task.manager.domain.repository.BookRepository

class GetBooks(private val repository: BookRepository) {

    suspend fun execute(): List<Book>  {
        return repository.getAll()
    }

}