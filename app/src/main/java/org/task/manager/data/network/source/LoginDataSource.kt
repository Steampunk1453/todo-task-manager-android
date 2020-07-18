package org.task.manager.data.network.source

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.data.network.source.DataSourceProvider
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun login(request: LoginRequest): LoginResponse {
        val loginDataSource = dataSourceProvider.getLoginDataSource()
        val response = loginDataSource.login(request)

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException("Empty response body")
    }

    fun logout() {
        // TODO: revoke authentication
    }
}