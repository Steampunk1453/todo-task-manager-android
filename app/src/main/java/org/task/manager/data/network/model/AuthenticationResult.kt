package org.task.manager.data.network.model

import org.task.manager.data.network.model.response.AuthenticationState

data class AuthenticationResult(
    var state: AuthenticationState,
    val message: String
)

