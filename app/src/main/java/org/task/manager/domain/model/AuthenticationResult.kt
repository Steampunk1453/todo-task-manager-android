package org.task.manager.domain.model

data class AuthenticationResult(
    var state: AuthenticationState,
    val message: String
)

