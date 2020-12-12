package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Book

data class BookResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("title")
    val title: String,

    @SerializedName("author")
    var author: String,

    @SerializedName("genre")
    var genre: String,

    @SerializedName("editorial")
    var editorial: String,

    @SerializedName("editorialUrl")
    var editorialUrl: String,

    @SerializedName("bookshop")
    var bookshop: String,

    @SerializedName("bookshopUrl")
    var bookshopUrl: String,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("deadline")
    val deadline: String,

    @SerializedName("check")
    val check: Int,

    @SerializedName("user")
    val userResponse: UserResponse?
)

fun BookResponse.toDomain(): Book = Book(
    id, title, author, genre, editorial, editorialUrl,
    bookshop, bookshopUrl, startDate, deadline, check, userResponse?.toDomain()
)

fun Book.toResponse(): BookResponse = BookResponse(
    id, title, author, genre, editorial, editorialUrl,
    bookshop, bookshopUrl, startDate, deadline, check, user?.toResponse()
)
