package org.task.manager.data.network.source

import org.task.manager.data.network.model.request.RegisterRequest

class RegisterDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun register(request: RegisterRequest) {
        val registerDataSource = dataSourceProvider.getRegisterDataSource()
        registerDataSource.register(request)
    }

}