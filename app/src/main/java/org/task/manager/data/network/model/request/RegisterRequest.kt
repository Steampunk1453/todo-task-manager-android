package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for register in users retrieved from RegisterRepository
 */
data class RegisterRequest(
        @SerializedName("login")
        val username: String,

        @SerializedName("email")
        val email: String,

        @SerializedName("password")
        val password: String
)