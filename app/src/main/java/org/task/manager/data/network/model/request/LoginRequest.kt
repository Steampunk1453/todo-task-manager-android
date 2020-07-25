package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
        @SerializedName("username")
        val username: String,

        @SerializedName("password")
        val password: String,

        @SerializedName("rememberMe")
        val isRememberMe: Boolean
)