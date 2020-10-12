package org.task.manager.domain.usecase.book

import org.task.manager.domain.model.Book
import org.task.manager.domain.repository.BookRepository

class GetBook(private val repository: BookRepository) {

    suspend fun execute(id: Long): Book {
       return repository.get(id)
    }

}