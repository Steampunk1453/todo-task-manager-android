package org.task.manager.domain.model

import org.task.manager.domain.model.state.AuthenticationState

data class AuthenticationResult(
    var state: AuthenticationState,
    val message: String
)

