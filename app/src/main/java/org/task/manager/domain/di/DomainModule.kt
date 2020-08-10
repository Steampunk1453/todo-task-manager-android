package org.task.manager.domain.di

import org.koin.dsl.module
import org.task.manager.domain.usecase.GetAudiovisuals
import org.task.manager.domain.usecase.LoginUser
import org.task.manager.domain.usecase.LogoutUser
import org.task.manager.domain.usecase.RegisterUser
import org.task.manager.shared.service.SessionManagerService

val modules = module {
    single { LoginUser(get(), get()) }
    single { LogoutUser(get()) }
    single { RegisterUser(get()) }
    single { SessionManagerService(get()) }
    single { GetAudiovisuals(get()) }
}

val domainModules = listOf(modules)