package org.task.manager.domain.usecase.book

import org.task.manager.data.network.model.request.BookRequest
import org.task.manager.domain.model.Book
import org.task.manager.domain.repository.BookRepository

class CreateBook(private val repository: BookRepository) {

    suspend fun execute(bookRequest: BookRequest): Book {
       return repository.save(bookRequest)
    }

}