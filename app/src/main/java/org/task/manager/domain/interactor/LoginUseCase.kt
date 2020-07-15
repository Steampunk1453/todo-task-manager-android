package org.task.manager.domain.interactor

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.domain.Result
import org.task.manager.domain.repository.LoginRepository
import org.task.manager.shared.SessionManager

class LoginUseCase(private val loginRepository: LoginRepository,
                   private val sessionManager: SessionManager) {


    suspend operator fun invoke(request: LoginRequest): Result<LoginResponse> {
//        sessionManager.saveAuthToken("")
        return loginRepository.login(request)
    }

}