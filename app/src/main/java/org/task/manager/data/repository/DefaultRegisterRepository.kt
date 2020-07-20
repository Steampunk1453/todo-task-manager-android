package org.task.manager.data.repository

import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.data.network.source.LoginDataSource
import org.task.manager.data.network.source.RegisterDataSource
import org.task.manager.domain.Result
import org.task.manager.domain.repository.RegisterRepository

class DefaultRegisterRepository(private val dataSource: RegisterDataSource) : RegisterRepository {

    override suspend fun register(request: RegisterRequest): Result<String> =
        try {
            dataSource.register(request)
            Result.Success("OK")
        } catch (ex: Throwable) {
            Result.Error(ex)
        }

}