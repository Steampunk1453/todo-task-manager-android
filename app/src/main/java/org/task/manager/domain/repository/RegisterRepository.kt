package org.task.manager.domain.repository

import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.domain.Result

interface RegisterRepository {
    suspend fun register(request: RegisterRequest): Result<String>
}