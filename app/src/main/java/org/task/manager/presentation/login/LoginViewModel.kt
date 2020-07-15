package org.task.manager.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.domain.interactor.LoginUseCase

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val authenticationState = MutableLiveData<AuthenticationState>()
    var username: String

    init {
        // In this example, the user is always unauthenticated when MainActivity is launched
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
        username = ""
    }

    fun refuseAuthentication() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun authenticate(username: String, password: String, isRemember: Boolean) {
        val loginRequest = LoginRequest(username, password, isRemember)
        coroutineScope.launch {
          val result = loginUseCase.execute(loginRequest)
            if (result) {
//                this.username = username
                authenticationState.postValue(AuthenticationState.AUTHENTICATED)
            } else {
                authenticationState.postValue(AuthenticationState.INVALID_AUTHENTICATION)
            }
        }
    }

}
