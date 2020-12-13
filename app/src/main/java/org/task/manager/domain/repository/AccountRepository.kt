package org.task.manager.domain.repository

import org.task.manager.data.network.model.request.PasswordRequest
import org.task.manager.data.network.model.request.ResetPasswordRequest
import org.task.manager.domain.Result
import org.task.manager.domain.model.User

interface AccountRepository {
    suspend fun register(user: User): Result<String>
    suspend fun activate(key: String): Result<String>
    suspend fun get(): Result<User>
    suspend fun save(user: User): Result<String>
    suspend fun changePassword(passwordRequest: PasswordRequest): Result<String>
    suspend fun requestPasswordReset(mail: String): Result<String>
    suspend fun finishPasswordReset(resetPasswordRequest: ResetPasswordRequest): Result<String>
}