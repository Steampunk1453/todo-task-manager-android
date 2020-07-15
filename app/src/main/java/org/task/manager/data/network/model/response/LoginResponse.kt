package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("status_code")
    var statusCode: Int,

    @SerializedName("id_token")
    var authToken: String
)