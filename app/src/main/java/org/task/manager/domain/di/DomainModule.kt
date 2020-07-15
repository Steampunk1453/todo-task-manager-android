package org.task.manager.domain.di

import org.koin.dsl.module
import org.task.manager.domain.interactor.LoginUseCase
import org.task.manager.shared.SessionManager

val modules = module {
    single { LoginUseCase(get(), get()) }
    single { SessionManager(get()) }
}

val domainModules = listOf(modules)