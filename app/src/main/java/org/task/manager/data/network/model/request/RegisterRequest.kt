package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("login")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("langKey")
    var langKey: String
)