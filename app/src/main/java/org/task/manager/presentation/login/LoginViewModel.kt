package org.task.manager.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.AuthenticationResult
import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.AuthenticationState
import org.task.manager.domain.Result
import org.task.manager.domain.interactor.LoginUseCase

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val authenticationResult = MutableLiveData<AuthenticationResult>()
    var username: String

    init {
        // In this example, the user is always unauthenticated when MainActivity is launched
        authenticationResult.value?.state = AuthenticationState.UNAUTHENTICATED
        username = ""
    }

    fun refuseAuthentication() {
        authenticationResult.value?.state = AuthenticationState.UNAUTHENTICATED
    }

    fun authenticate(username: String, password: String, isRemember: Boolean) {

        val loginRequest = LoginRequest(username, password, isRemember)
        coroutineScope.launch {
            when (val result = loginUseCase(loginRequest)) {
                is Result.Success -> authenticationResult.postValue(AuthenticationResult(AuthenticationState.AUTHENTICATED, "Ok"))
                is Result.Error ->
                    result.throwable.message?.let {
                        authenticationResult.postValue(AuthenticationResult(AuthenticationState.INVALID_AUTHENTICATION, it))
                    }
            }
        }
    }

    public override fun onCleared() {
        coroutineScope.cancel()
    }


}
