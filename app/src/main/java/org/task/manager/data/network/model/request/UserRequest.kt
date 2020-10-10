package org.task.manager.data.network.model.request

import com.google.gson.annotations.SerializedName
import org.task.manager.domain.model.User

data class UserRequest(
    @SerializedName("id")
    val id: Long
)

fun User.toRequest(): UserRequest = UserRequest(id)
