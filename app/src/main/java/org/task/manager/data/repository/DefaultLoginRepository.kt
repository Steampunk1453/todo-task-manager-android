package org.task.manager.data.repository

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.data.network.source.LoginDataSource
import org.task.manager.domain.repository.LoginRepository
import org.task.manager.domain.Result

class DefaultLoginRepository(private val dataSource: LoginDataSource) : LoginRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> =
        try {
            val loginResponse = dataSource.login(request).let { LoginResponse(it.statusCode, it.authToken) }
            Result.Success(loginResponse)
        } catch (ex: Throwable) {
          Result.Error(ex)
        }

}