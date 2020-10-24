package org.task.manager.domain.repository

import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.data.network.model.request.UserRequest
import org.task.manager.domain.Result
import org.task.manager.domain.model.User

interface AccountRepository {
    suspend fun register(request: RegisterRequest): Result<String>
    suspend fun activate(key: String): Result<String>
    suspend fun get(): User
    suspend fun save(userRequest: UserRequest): Result<String>
}