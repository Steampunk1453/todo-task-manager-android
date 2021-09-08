package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Title

data class TitleResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String?,
    @SerializedName("rank")
    val rank: Int?,
    @SerializedName("type")
    val type: String,
    @SerializedName("genres")
    val genres: String?,
    @SerializedName("platform")
    val platform: String?,
    @SerializedName("website")
    val website: String?
)

fun TitleResponse.toDomain(): Title = Title(id, title, rank, type, genres, platform, website)

fun Title.toResponse(): TitleResponse = TitleResponse(id, title, rank, type, genres, platform, website)


