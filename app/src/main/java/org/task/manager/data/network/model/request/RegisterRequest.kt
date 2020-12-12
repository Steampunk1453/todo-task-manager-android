package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.User

data class RegisterRequest(
    @SerializedName("login")
    val username: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("password")
    val password: String?,

    @SerializedName("langKey")
    var langKey: String?
)

fun User.toRegisterRequest(): RegisterRequest = RegisterRequest(username, email, password, langKey)