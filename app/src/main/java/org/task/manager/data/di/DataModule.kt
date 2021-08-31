package org.task.manager.data.di

import androidx.room.Room
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.task.manager.data.local.AppDatabase
import org.task.manager.data.local.AppDatabase.Companion.MIGRATION_1_2
import org.task.manager.data.local.source.AudiovisualLocalDataSource
import org.task.manager.data.local.source.BookLocalDataSource
import org.task.manager.data.local.source.GenreLocalDataSource
import org.task.manager.data.local.source.RemoveLocalDataSource
import org.task.manager.data.network.NetworkServiceFactory
import org.task.manager.data.network.ServiceFactory
import org.task.manager.data.network.api.*
import org.task.manager.data.network.source.*
import org.task.manager.data.repository.*
import org.task.manager.domain.repository.*
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
            .addMigrations(MIGRATION_1_2)
            .build()
    }
    single { get<AppDatabase>().audiovisualDao() }
    single { get<AppDatabase>().titleDao() }
    single { get<AppDatabase>().platformDao() }
    single { get<AppDatabase>().bookDao() }
    single { get<AppDatabase>().editorialDao() }
    single { get<AppDatabase>().bookshopDao() }
    single { get<AppDatabase>().genreDao() }
}

val repositoryModule = module {
    factory {
        val dataSourceProvider = DataSourceProvider(get())
        dataSourceProvider
    }

    single { LoginDataSource(get()) }
    single { AccountDataSource(get()) }
    single { AudiovisualRemoteDataSource(get()) }
    single { AudiovisualLocalDataSource(get(), get(), get()) }
    single { BookRemoteDataSource(get()) }
    single { BookLocalDataSource(get(), get(), get()) }
    single { GenreRemoteDataSource(get()) }
    single { GenreLocalDataSource(get()) }
    single { CalendarDataSource() }
    single { RemoveLocalDataSource(get(), get(), get(), get(), get(), get(), get()) }

    single<LoginRepository> { DefaultLoginRepository(get(), get()) }
    single<AccountRepository> { DefaultAccountRepository(get()) }
    single<AudiovisualRepository> { DefaultAudiovisualRepository(get(), get()) }
    single<GenreRepository> { DefaultGenreRepository(get(), get()) }
    single<BookRepository> { DefaultBookRepository(get(), get()) }
    single<CalendarRepository> { DefaultCalendarRepository(get()) }
}

val dataModules = networkModule + databaseModule + repositoryModule