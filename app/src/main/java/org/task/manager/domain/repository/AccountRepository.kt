package org.task.manager.domain.repository

import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.domain.Result

interface AccountRepository {
    suspend fun register(request: RegisterRequest): Result<String>
    suspend fun activate(key: String): Result<String>
}