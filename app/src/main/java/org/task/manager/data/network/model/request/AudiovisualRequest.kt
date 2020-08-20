package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName

data class AudiovisualRequest(
    @SerializedName("title")
    val title: String,

    @SerializedName("genre")
    val genre: String,

    @SerializedName("platform")
    val platform: String,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("deadline")
    val deadline: String,

    @SerializedName("check")
    val check: Int
)

