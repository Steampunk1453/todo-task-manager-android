package org.task.manager.data.network.source

import org.task.manager.data.network.ServiceFactory
import org.task.manager.data.network.api.AccountApi
import org.task.manager.data.network.api.AudiovisualApi
import org.task.manager.data.network.api.BookApi
import org.task.manager.data.network.api.GenreApi
import org.task.manager.data.network.api.LoginApi


class DataSourceProvider(private val serviceFactory: ServiceFactory) {
    fun getLoginDataSource(): LoginApi {
        return serviceFactory.create(LoginApi::class.java)
    }

    fun getAccountDataSource(): AccountApi {
        return serviceFactory.create(AccountApi::class.java)
    }

    fun getAudiovisualDataSource(): AudiovisualApi {
        return serviceFactory.create(AudiovisualApi::class.java)
    }

    fun getGenreDataSource(): GenreApi {
        return serviceFactory.create(GenreApi::class.java)
    }

    fun getBookDataSource(): BookApi {
        return serviceFactory.create(BookApi::class.java)
    }
}