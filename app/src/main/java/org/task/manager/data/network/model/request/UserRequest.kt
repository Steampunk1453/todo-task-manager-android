package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("email")
    val email: String
)

