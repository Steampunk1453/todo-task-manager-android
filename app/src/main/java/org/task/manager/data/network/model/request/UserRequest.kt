package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("login")
    val username: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("langKey")
    var langKey: String
)

