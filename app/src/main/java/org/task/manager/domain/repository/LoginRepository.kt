package org.task.manager.domain.repository

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.domain.Result

interface LoginRepository {
    suspend fun login(request: LoginRequest): Result<LoginResponse>
}