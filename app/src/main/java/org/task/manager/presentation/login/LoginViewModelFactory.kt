//package org.task.manager.presentation.login
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import org.task.manager.data.network.source.LoginDataSource
//import org.task.manager.data.repository.DefaultLoginRepository
//import org.task.manager.domain.interactor.LoginUseCase
//import org.task.manager.domain.repository.LoginRepository
//import org.task.manager.shared.SessionManager
//
///**
// * ViewModel provider factory to instantiate LoginViewModel.
// * Required given LoginViewModel has a non-empty constructor
// */
//class LoginViewModelFactory : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            return LoginViewModel(LoginUseCase(loginRepository = LoginRepository, sessionManager = SessionManager())) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}