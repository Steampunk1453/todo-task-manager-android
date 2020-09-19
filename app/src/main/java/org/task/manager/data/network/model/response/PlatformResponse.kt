package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Platform

data class PlatformResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)

fun PlatformResponse.toDomain(): Platform = Platform(id, name, url)

