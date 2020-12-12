package org.task.manager.presentation.book

import org.task.manager.domain.model.Book
import org.task.manager.domain.model.User

data class BookDto(
    val id: Long?,
    val title: String,
    val author: String,
    val genre: String,
    val editorial: String,
    val editorialUrl: String,
    val bookshop: String,
    val bookshopUrl: String,
    val startDate: String,
    val deadline: String,
    val check: Int,
    val userId: Long?
)

fun BookDto.toDomain(): Book = Book(id, title, author, genre, editorial, editorialUrl,
    bookshop, bookshopUrl, startDate, deadline, check, toUserDomain()
)

fun BookDto.toUserDomain(): User = User(userId, null, null, null, null
)
