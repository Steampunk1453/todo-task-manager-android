package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("id")
    val id: Long
)

