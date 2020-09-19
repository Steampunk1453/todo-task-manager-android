package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Genre

data class GenreResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("literary")
    val literary: Int
)

fun GenreResponse.toDomain(): Genre = Genre(id, name, literary)

