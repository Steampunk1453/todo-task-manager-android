package org.task.manager.data.repository

import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.data.network.source.AccountDataSource
import org.task.manager.domain.Result
import org.task.manager.domain.repository.AccountRepository

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
            Result.Success("OK")
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }
}