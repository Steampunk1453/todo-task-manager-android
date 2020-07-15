package org.task.manager.data.repository

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.data.network.source.LoginDataSource
import org.task.manager.domain.repository.LoginRepository

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class DefaultLoginRepository(private val dataSource: LoginDataSource) : LoginRepository {

    override suspend fun login(request: LoginRequest): LoginResponse =
        dataSource.login(request).let { LoginResponse(it.statusCode, it.authToken) }
}