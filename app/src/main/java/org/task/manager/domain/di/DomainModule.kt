package org.task.manager.domain.di

import org.koin.dsl.module
import org.task.manager.domain.usecase.audiovisual.CreateAudiovisual
import org.task.manager.domain.usecase.audiovisual.DeleteAudiovisual
import org.task.manager.domain.usecase.audiovisual.GetAudiovisual
import org.task.manager.domain.usecase.audiovisual.GetAudiovisuals
import org.task.manager.domain.usecase.audiovisual.GetPlatforms
import org.task.manager.domain.usecase.audiovisual.GetTitles
import org.task.manager.domain.usecase.audiovisual.UpdateAudiovisual
import org.task.manager.domain.usecase.book.CreateBook
import org.task.manager.domain.usecase.book.DeleteBook
import org.task.manager.domain.usecase.book.GetBook
import org.task.manager.domain.usecase.book.GetBooks
import org.task.manager.domain.usecase.book.GetBookshops
import org.task.manager.domain.usecase.book.GetEditorials
import org.task.manager.domain.usecase.book.UpdateBook
import org.task.manager.domain.usecase.shared.GetGenres
import org.task.manager.domain.usecase.user.ActivateUser
import org.task.manager.domain.usecase.user.GetUser
import org.task.manager.domain.usecase.user.LoginUser
import org.task.manager.domain.usecase.user.LogoutUser
import org.task.manager.domain.usecase.user.RegisterUser
import org.task.manager.domain.usecase.user.UpdateUser
import org.task.manager.domain.usecase.user.password.UpdatePassword
import org.task.manager.shared.service.SessionManagerService

val modules = module {

    single { LoginUser(get(), get()) }
    single { LogoutUser(get()) }
    single { RegisterUser(get()) }
    single { ActivateUser(get()) }
    single { GetUser(get()) }
    single { UpdateUser(get()) }
    single { UpdatePassword(get()) }

    single { SessionManagerService(get()) }

    single { GetAudiovisuals(get()) }
    single { GetAudiovisual(get()) }
    single { CreateAudiovisual(get()) }
    single { UpdateAudiovisual(get()) }
    single { DeleteAudiovisual(get()) }
    single { GetTitles(get()) }
    single { GetPlatforms(get()) }

    single { GetBooks(get()) }
    single { GetBook(get()) }
    single { CreateBook(get()) }
    single { UpdateBook(get()) }
    single { DeleteBook(get()) }
    single { GetBookshops(get()) }
    single { GetEditorials(get()) }

    single { GetGenres(get()) }

}

val domainModules = listOf(modules)