package org.task.manager.domain.usecase.user

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.domain.Result
import org.task.manager.domain.model.AuthenticationResult
import org.task.manager.domain.model.AuthenticationState
import org.task.manager.domain.repository.LoginRepository
import org.task.manager.shared.service.SessionManagerService
import timber.log.Timber

private const val SUCCESSFUL_AUTHENTICATION = "Successful authentication"

class LoginUser(private val repository: LoginRepository,
                private val sessionManager: SessionManagerService) {


    suspend fun execute(request: LoginRequest): AuthenticationResult? =
        when (val result = repository.login(request)) {
            is Result.Success -> manageSuccessfulResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailedResponse(it)
            }
        }

    private fun manageSuccessfulResponse(loginResponse: LoginResponse): AuthenticationResult {
        saveToken(loginResponse.authToken)
        Timber.d(SUCCESSFUL_AUTHENTICATION)
        return AuthenticationResult(
            AuthenticationState.AUTHENTICATED, SUCCESSFUL_AUTHENTICATION
        )
    }

    private fun manageFailedResponse(error: String): AuthenticationResult {
        Timber.e("Invalid Authentication: %s", error)
        return AuthenticationResult(
            AuthenticationState.INVALID_AUTHENTICATION, error
        )
    }

    private fun saveToken(token: String) {
        sessionManager.saveAuthToken(token)
    }

}