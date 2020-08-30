package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName

data class AudiovisualResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("genre")
    val genre: String,

    @SerializedName("platform")
    val platform: String,

    @SerializedName("platformUrl")
    val platformUrl: String?,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("deadline")
    val deadline: String,

    @SerializedName("check")
    val check: Int
)

