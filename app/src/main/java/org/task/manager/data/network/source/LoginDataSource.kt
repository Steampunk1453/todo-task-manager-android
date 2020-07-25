package org.task.manager.data.network.source

import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import java.io.IOException


class LoginDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun login(request: LoginRequest): LoginResponse {
        val loginApi = dataSourceProvider.getLoginDataSource()
        val response = loginApi.login(request)

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException("Empty response body")
    }

}