package org.task.manager.data.network.source

import org.task.manager.data.network.model.request.RegisterRequest

class AccountDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun register(request: RegisterRequest) {
        val registerApi = dataSourceProvider.getRegisterDataSource()
        registerApi.register(request)
    }

    suspend fun activate(key: String) {
        val registerApi = dataSourceProvider.getRegisterDataSource()
        registerApi.activate(key)
    }

}