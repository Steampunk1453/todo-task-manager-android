package org.task.manager.domain.usecase.book

import org.task.manager.domain.repository.BookRepository

class DeleteBook(private val repository: BookRepository) {

    suspend fun execute(id: Long) {
       return repository.remove(id)
    }

}