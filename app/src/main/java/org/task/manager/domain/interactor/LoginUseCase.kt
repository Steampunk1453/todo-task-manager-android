package org.task.manager.domain.interactor

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.domain.repository.LoginRepository
import org.task.manager.shared.SessionManager

class LoginUseCase(private val loginRepository: LoginRepository,
                   private val sessionManager: SessionManager) {


    suspend fun execute(request: LoginRequest): Boolean {
        val response = loginRepository.login(request)
        sessionManager.saveAuthToken(response.authToken)
        return true
    }

}