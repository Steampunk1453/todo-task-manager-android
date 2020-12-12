package org.task.manager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.task.manager.domain.model.Book
import org.task.manager.domain.model.User

@Entity(tableName = "Book")
data class BookEntity(
    @PrimaryKey
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

fun BookEntity.toDomain(): Book = Book(
    id, title, author, genre, editorial, editorialUrl,
    bookshop, bookshopUrl, startDate, deadline, check, toUserDomain()
)

fun BookEntity.toUserDomain(): User = User(userId)

fun Book.toEntity(): BookEntity = BookEntity(
    id, title, author, genre, editorial, editorialUrl,
    bookshop, bookshopUrl, startDate, deadline, check, user?.id
)

