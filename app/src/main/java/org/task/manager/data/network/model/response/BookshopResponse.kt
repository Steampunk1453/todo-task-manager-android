package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName

data class BookshopResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)



