package org.task.manager.data.di

import androidx.room.Room
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.task.manager.data.local.AppDatabase
import org.task.manager.data.local.source.AudiovisualLocalDataSource
import org.task.manager.data.local.source.BookLocalDataSource
import org.task.manager.data.local.source.RemoveLocalDataSource
import org.task.manager.data.network.NetworkServiceFactory
import org.task.manager.data.network.ServiceFactory
import org.task.manager.data.network.api.AccountApi
import org.task.manager.data.network.api.AudiovisualApi
import org.task.manager.data.network.api.BookApi
import org.task.manager.data.network.api.GenreApi
import org.task.manager.data.network.api.LoginApi
import org.task.manager.data.network.source.AccountDataSource
import org.task.manager.data.network.source.AudiovisualRemoteDataSource
import org.task.manager.data.network.source.BookRemoteDataSource
import org.task.manager.data.network.source.CalendarDataSource
import org.task.manager.data.network.source.DataSourceProvider
import org.task.manager.data.network.source.GenreDataSource
import org.task.manager.data.network.source.LoginDataSource
import org.task.manager.data.repository.DefaultAccountRepository
import org.task.manager.data.repository.DefaultAudiovisualRepository
import org.task.manager.data.repository.DefaultBookRepository
import org.task.manager.data.repository.DefaultCalendarRepository
import org.task.manager.data.repository.DefaultGenreRepository
import org.task.manager.data.repository.DefaultLoginRepository
import org.task.manager.domain.repository.AccountRepository
import org.task.manager.domain.repository.AudiovisualRepository
import org.task.manager.domain.repository.BookRepository
import org.task.manager.domain.repository.CalendarRepository
import org.task.manager.domain.repository.GenreRepository
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

    single { get<Retrofit>().create(LoginApi::class.java) }
    single { get<Retrofit>().create(AccountApi::class.java) }
    single { get<Retrofit>().create(AudiovisualApi::class.java) }
    single { get<Retrofit>().create(GenreApi::class.java) }
    single { get<Retrofit>().create(BookApi::class.java) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }
    single { get<AppDatabase>().audiovisualDao() }
    single { get<AppDatabase>().bookDao() }
}

val repositoryModule = module {
    factory {
        val dataSourceProvider = DataSourceProvider(get())
        dataSourceProvider
    }

    single { LoginDataSource(get()) }
    single { AccountDataSource(get()) }
    single { AudiovisualRemoteDataSource(get()) }
    single { AudiovisualLocalDataSource(get()) }
    single { GenreDataSource(get()) }
    single { BookRemoteDataSource(get()) }
    single { BookLocalDataSource(get()) }
    single { CalendarDataSource() }
    single { RemoveLocalDataSource(get(), get()) }

    single<LoginRepository> { DefaultLoginRepository(get(), get()) }
    single<AccountRepository> { DefaultAccountRepository(get()) }
    single<AudiovisualRepository> { DefaultAudiovisualRepository(get(), get()) }
    single<GenreRepository> { DefaultGenreRepository(get()) }
    single<BookRepository> { DefaultBookRepository(get(), get()) }
    single<CalendarRepository> { DefaultCalendarRepository(get()) }
}

val dataModules = networkModule + databaseModule + repositoryModule