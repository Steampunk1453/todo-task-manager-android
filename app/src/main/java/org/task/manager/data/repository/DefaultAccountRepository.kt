package org.task.manager.data.repository

import org.task.manager.data.network.model.request.PasswordRequest
import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.data.network.model.request.UserRequest
import org.task.manager.data.network.model.response.toDomain
import org.task.manager.data.network.source.AccountDataSource
import org.task.manager.domain.Result
import org.task.manager.domain.model.User
import org.task.manager.domain.repository.AccountRepository

private const val SUCCESSFUL_RESPONSE = "OK"

class DefaultAccountRepository(private val dataSource: AccountDataSource) : AccountRepository {

    override suspend fun register(request: RegisterRequest): Result<String> {
        return try {
            dataSource.register(request)
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

    override suspend fun save(userRequest: UserRequest): Result<String> {
        return try {
            dataSource.save(userRequest)
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

}