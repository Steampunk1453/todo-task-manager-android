package org.task.manager.data.network.source

import org.task.manager.data.network.model.request.RegisterRequest

class RegisterDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun register(request: RegisterRequest) {
        val registerApi = dataSourceProvider.getRegisterDataSource()
        registerApi.register(request)
    }

}