package org.task.manager.data.di

import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.task.manager.data.network.NetworkServiceFactory
import org.task.manager.data.network.ServiceFactory
import org.task.manager.data.network.api.LoginApi
import org.task.manager.data.network.api.RegisterApi
import org.task.manager.data.network.source.DataSourceProvider
import org.task.manager.data.network.source.LoginDataSource
import org.task.manager.data.network.source.RegisterDataSource
import org.task.manager.data.repository.DefaultLoginRepository
import org.task.manager.data.repository.DefaultRegisterRepository
import org.task.manager.domain.repository.LoginRepository
import org.task.manager.domain.repository.RegisterRepository
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
        val dataSourceProvider: DataSourceProvider = DataSourceProvider(get())
        dataSourceProvider
    }

    single {
        LoginDataSource(get())
    }

    single {
        RegisterDataSource(get())
    }

    single<LoginRepository> {
        DefaultLoginRepository(get())
    }

    single<RegisterRepository> {
        DefaultRegisterRepository(get())
    }

    single { get<Retrofit>().create(LoginApi::class.java) }
    single { get<Retrofit>().create(RegisterApi::class.java) }
}

val dataModules = networkModule