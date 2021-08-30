package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Title

data class TitleResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("genres")
    val genres: String,
    @SerializedName("platform")
    val platform: String?,
    @SerializedName("website")
    val website: String?
)

fun TitleResponse.toDomain(): Title = Title(id, title, type, genres, platform, website)

fun Title.toResponse(): TitleResponse = TitleResponse(id, title, type, genres, platform, website)


