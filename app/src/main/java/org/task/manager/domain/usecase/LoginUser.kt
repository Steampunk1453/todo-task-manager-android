package org.task.manager.domain.usecase

import org.task.manager.domain.model.AuthenticationResult
import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.domain.model.AuthenticationState
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.domain.Result
import org.task.manager.domain.repository.LoginRepository
import org.task.manager.shared.service.SessionManagerService
import timber.log.Timber

class LoginUser(private val loginRepository: LoginRepository,
                private val sessionManagerService: SessionManagerService) {


    suspend fun execute(request: LoginRequest): AuthenticationResult? =
        when (val result = loginRepository.login(request)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailedResponse(it)
            }
        }

    private fun manageSuccessResponse(loginResponse: LoginResponse): AuthenticationResult {
        saveToken(loginResponse.authToken)
        Timber.d("Successful authentication")
        return AuthenticationResult(
            AuthenticationState.AUTHENTICATED,
            "Successful authentication"
        )
    }


    private fun manageFailedResponse(error: String): AuthenticationResult {
        Timber.e("Invalid Authentication: %s", error)
        return AuthenticationResult(
            AuthenticationState.INVALID_AUTHENTICATION,
            error
        )
    }

    private fun saveToken(token: String) {
        sessionManagerService.saveAuthToken(token)
    }

}