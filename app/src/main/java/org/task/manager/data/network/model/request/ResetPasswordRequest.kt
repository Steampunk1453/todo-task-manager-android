package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
        @SerializedName("key")
        val key: String,

        @SerializedName("newPassword")
        val newPassword: String
)