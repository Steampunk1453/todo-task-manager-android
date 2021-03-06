package org.task.manager.data.repository

import org.task.manager.data.local.source.RemoveLocalDataSource
import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.data.network.source.LoginDataSource
import org.task.manager.domain.Result
import org.task.manager.domain.repository.LoginRepository

class DefaultLoginRepository(private val dataSource: LoginDataSource,
                             private val remoteDataSource: RemoveLocalDataSource) : LoginRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val loginResponse = dataSource.login(request)
            remoteDataSource.deleteAll()
            Result.Success(loginResponse)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }

}