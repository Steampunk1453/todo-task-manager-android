package org.task.manager.domain.usecase.user

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.domain.Result
import org.task.manager.domain.model.state.AuthenticationState
import org.task.manager.domain.repository.LoginRepository
import org.task.manager.shared.service.SessionManagerService
import timber.log.Timber

class LoginUser(private val repository: LoginRepository,
                private val sessionManager: SessionManagerService) {


    suspend fun execute(request: LoginRequest): AuthenticationState? {
       return when (val result = repository.login(request)) {
            is Result.Success -> handleSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let { handleFailedResponse(it) }
        }
    }

    private fun handleSuccessResponse(loginResponse: LoginResponse): AuthenticationState {
        saveToken(loginResponse.authToken)
        Timber.d("Successful authentication: %s", loginResponse.statusCode)
        return AuthenticationState.AUTHENTICATED

    }

    private fun handleFailedResponse(error: String): AuthenticationState {
        Timber.e("Invalid Authentication: %s", error)
        return AuthenticationState.INVALID_AUTHENTICATION
    }

    private fun saveToken(token: String) {
        sessionManager.saveAuthToken(token)
    }

}