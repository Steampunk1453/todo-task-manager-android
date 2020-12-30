package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Editorial

data class EditorialResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)

fun EditorialResponse.toDomain(): Editorial = Editorial(id, name, url)

fun Editorial.toResponse(): EditorialResponse = EditorialResponse(id, name, url)


