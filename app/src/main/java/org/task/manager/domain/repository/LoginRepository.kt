package org.task.manager.domain.repository

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse

interface LoginRepository {
    suspend fun login(request: LoginRequest): LoginResponse
}