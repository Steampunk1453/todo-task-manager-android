package org.task.manager.data.repository

import org.task.manager.data.network.model.request.PasswordRequest
import org.task.manager.data.network.model.request.ResetPasswordRequest
import org.task.manager.data.network.model.request.toRegisterRequest
import org.task.manager.data.network.model.request.toRequest
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.AccountDataSource
import org.task.manager.domain.Result
import org.task.manager.domain.model.User
import org.task.manager.domain.repository.AccountRepository

private const val SUCCESSFUL_RESPONSE = "OK"

class DefaultAccountRepository(private val dataSource: AccountDataSource) : AccountRepository {

    override suspend fun register(user: User): Result<String> {
        return try {
            dataSource.register(user.toRegisterRequest())
            Result.Success("OK")
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun activate(key: String): Result<String> {
        return try {
            dataSource.activate(key)
            Result.Success(SUCCESSFUL_RESPONSE)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun get(): User {
        val userResponse = dataSource.get()
        return userResponse.toDomain()
    }

    override suspend fun save(user: User): Result<String> {
        return try {
            dataSource.save(user.toRequest())
            Result.Success(SUCCESSFUL_RESPONSE)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun changePassword(passwordRequest: PasswordRequest): Result<String> {
        return try {
            dataSource.changePassword(passwordRequest)
            Result.Success(SUCCESSFUL_RESPONSE)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun requestPasswordReset(mail: String): Result<String> {
        return try {
            dataSource.requestPasswordReset(mail)
            Result.Success(SUCCESSFUL_RESPONSE)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

    override suspend fun finishPasswordReset(resetPasswordRequest: ResetPasswordRequest): Result<String> {
        return try {
            dataSource.finishPasswordReset(resetPasswordRequest)
            Result.Success(SUCCESSFUL_RESPONSE)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

}