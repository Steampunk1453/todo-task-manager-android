package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.User

data class UserRequest(
    @SerializedName("login")
    val username: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("langKey")
    var langKey: String?,

    @SerializedName("firstName")
    val firstName: String?,

    @SerializedName("lastName")
    val lastName: String?

)

fun User.toRequest(): UserRequest = UserRequest(
    username, email, langKey, firstName, lastName
)
