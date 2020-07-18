package org.task.manager.domain.di

import org.koin.dsl.module
import org.task.manager.domain.interactor.LoginUseCase
import org.task.manager.shared.SessionManagerService

val modules = module {
    single { LoginUseCase(get(), get()) }
    single { SessionManagerService(get()) }
}

val domainModules = listOf(modules)