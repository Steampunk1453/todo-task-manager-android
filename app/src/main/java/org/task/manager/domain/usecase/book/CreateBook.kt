package org.task.manager.domain.usecase.book

import org.task.manager.domain.model.Book
import org.task.manager.domain.repository.BookRepository

class CreateBook(private val repository: BookRepository) {

    suspend fun execute(book: Book): Book {
       return repository.save(book)
    }

}