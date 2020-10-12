package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Book

data class BookRequest(
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
    val userRequest: UserRequest?
)

fun Book.toRequest(): BookRequest = BookRequest(null, title, author, genre, editorial,
    editorialUrl, bookshop, bookshopUrl, startDate, deadline, check, user?.toRequest()
)