package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Title

data class TitleResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String
)

fun TitleResponse.toDomain(): Title = Title(id, name)


