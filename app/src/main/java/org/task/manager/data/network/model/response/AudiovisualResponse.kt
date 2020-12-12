package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Audiovisual

data class AudiovisualResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("title")
    val title: String,

    @SerializedName("genre")
    val genre: String,

    @SerializedName("platform")
    val platform: String,

    @SerializedName("platformUrl")
    val platformUrl: String,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("deadline")
    val deadline: String,

    @SerializedName("check")
    val check: Int,

    @SerializedName("user")
    val userResponse: UserResponse?
)

fun AudiovisualResponse.toDomain(): Audiovisual = Audiovisual(id, title, genre, platform, platformUrl,
    startDate, deadline, check, userResponse?.toDomain()
)

fun Audiovisual.toResponse(): AudiovisualResponse = AudiovisualResponse(id, title, genre, platform, platformUrl,
    startDate, deadline, check, user?.toResponse()
)


