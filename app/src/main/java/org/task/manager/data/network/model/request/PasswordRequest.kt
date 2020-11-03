package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName

data class PasswordRequest(
        @SerializedName("currentPassword")
        val currentPassword: String,

        @SerializedName("newPassword")
        val newPassword: String
)