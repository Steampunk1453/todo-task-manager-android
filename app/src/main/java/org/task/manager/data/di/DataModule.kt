package org.task.manager.data.di

import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.task.manager.data.network.NetworkServiceFactory
import org.task.manager.data.network.ServiceFactory
import org.task.manager.data.network.api.LoginApi
import org.task.manager.data.network.source.DataSourceProvider
import org.task.manager.data.network.source.LoginDataSource
import org.task.manager.data.repository.DefaultLoginRepository
import org.task.manager.domain.repository.LoginRepository
import retrofit2.Retrofit

val networkModule = module {

    factory { HttpLoggingInterceptor.Level.BODY }

    single {
        val serviceFactory: ServiceFactory = NetworkServiceFactory(
            get(),
            get()
        )
        serviceFactory
    }

    factory {
        val serviceProvider: DataSourceProvider = DataSourceProvider(get())
        serviceProvider
    }

    single {
        LoginDataSource(get())
    }

    single<LoginRepository> {
        DefaultLoginRepository(get())
    }

    single { get<Retrofit>().create(LoginApi::class.java) }
}

val dataModules = networkModule