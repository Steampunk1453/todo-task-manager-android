package org.task.manager.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.task.manager.presentation.account.SettingsViewModel
import org.task.manager.presentation.audiovisual.AudiovisualViewModel
import org.task.manager.presentation.book.BookViewModel
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.user.login.LoginViewModel
import org.task.manager.presentation.user.registration.RegistrationViewModel

val module: Module = module {

    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { AudiovisualViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { BookViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }
    single { DateService() }

}

val presentationModules = listOf(module)
