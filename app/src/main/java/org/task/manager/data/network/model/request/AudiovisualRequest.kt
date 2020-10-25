package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.Audiovisual

data class AudiovisualRequest(
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
    val userIdRequest: UserIdRequest?
)

fun Audiovisual.toRequest(): AudiovisualRequest = AudiovisualRequest(null, title, genre, platform,
    platformUrl, startDate, deadline, check, user?.toRequest()
)

