package org.task.manager.data.network.model.response

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.User

data class UserResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("login")
    val username: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("firstName")
    val firstName: String?,

    @SerializedName("lastName")
    val lastName: String?
)

fun UserResponse.toDomain(): User = User(id, username, email, firstName, lastName)

fun User.toResponse(): UserResponse = UserResponse(id, username, email, firstName, lastName)
