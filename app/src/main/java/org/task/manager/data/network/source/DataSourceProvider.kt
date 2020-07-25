package org.task.manager.data.network.source

import org.task.manager.data.network.ServiceFactory
import org.task.manager.data.network.api.LoginApi
import org.task.manager.data.network.api.RegisterApi


class DataSourceProvider(private val serviceFactory: ServiceFactory) {
    fun getLoginDataSource(): LoginApi {
        return serviceFactory.create(LoginApi::class.java)
    }

    fun getRegisterDataSource(): RegisterApi {
        return serviceFactory.create(RegisterApi::class.java)
    }
}