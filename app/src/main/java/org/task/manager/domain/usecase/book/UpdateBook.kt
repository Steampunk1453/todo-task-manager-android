package org.task.manager.domain.usecase.book

import org.task.manager.domain.model.Book
import org.task.manager.domain.repository.BookRepository

class UpdateBook(private val repository: BookRepository) {

    suspend fun execute(book: Book): Book {
       return repository.update(book)
    }

}