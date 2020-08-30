package org.task.manager.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.task.manager.presentation.audiovisual.AudiovisualViewModel
import org.task.manager.presentation.auth.login.LoginViewModel
import org.task.manager.presentation.auth.registration.RegistrationViewModel
import org.task.manager.presentation.shared.SharedViewModel

val module: Module = module {

    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { SharedViewModel() }
    viewModel { AudiovisualViewModel(get(), get(), get()) }

}

val presentationModules = listOf(module)
