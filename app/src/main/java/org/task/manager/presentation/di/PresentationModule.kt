package org.task.manager.presentation.di


import org.koin.core.module.Module
import org.koin.dsl.module
import org.task.manager.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.task.manager.presentation.registration.RegistrationViewModel

val module: Module = module {

    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegistrationViewModel(get()) }

}

val presentationModules = listOf(module)
