package org.task.manager.data.network.source

import org.task.manager.data.network.model.request.PasswordRequest
import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.data.network.model.request.UserRequest
import org.task.manager.data.network.model.response.UserResponse
import org.task.manager.shared.Constants
import java.io.IOException

class AccountDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun register(request: RegisterRequest) {
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.register(request)
    }

    suspend fun activate(key: String) {
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.activate(key)
    }

    suspend fun get(): UserResponse {
        val accountApi = dataSourceProvider.getAccountDataSource()
        val response = accountApi.get()
        if (!response.isSuccessful) throw IOException(response.message())
        return response.body() ?: throw IllegalStateException(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

    suspend fun save(request: UserRequest) {
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.save(request)
    }

    suspend fun changePassword(request: PasswordRequest) {
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.changePassword(request)
    }

}