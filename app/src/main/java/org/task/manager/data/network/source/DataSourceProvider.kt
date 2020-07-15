package org.task.manager.data.network.source

import org.task.manager.data.network.ServiceFactory
import org.task.manager.data.network.api.LoginApi


class DataSourceProvider(private val serviceFactory: ServiceFactory) {
    fun getLoginDataSource(): LoginApi {
        return serviceFactory.create(LoginApi::class.java)
    }
}