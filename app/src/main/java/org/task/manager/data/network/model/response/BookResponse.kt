package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName

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

    @SerializedName("editorial_url")
    var editorialUrl: String,

    @SerializedName("bookshop")
    var bookshop: String,

    @SerializedName("bookshop")
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

