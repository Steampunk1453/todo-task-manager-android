package org.task.manager.presentation.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.domain.model.AuthenticationResult
import org.task.manager.domain.model.AuthenticationState
import org.task.manager.domain.usecase.user.LoginUser
import org.task.manager.domain.usecase.user.LogoutUser

class LoginViewModel(
    private val loginUser: LoginUser,
    private val logoutUser: LogoutUser
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val authenticationResult = MutableLiveData<AuthenticationResult>()
    val logoutState = MutableLiveData<LogoutState>()

    init {
        // In this case, the user is always unauthenticated when MainActivity is launched
        authenticationResult.value?.state = AuthenticationState.UNAUTHENTICATED
    }

    fun refuseAuthentication() {
        authenticationResult.value?.state = AuthenticationState.UNAUTHENTICATED
    }

    fun authenticate(username: String, password: String, isRemember: Boolean) {
        val loginRequest = LoginRequest(username, password, isRemember)
        coroutineScope.launch {
            val authenticationResponse = loginUser.execute(loginRequest)
            authenticationResult.postValue(authenticationResponse)
        }
    }

    fun singOut() {
        logoutUser.execute()
        logoutState.value = LogoutState.LOGOUT_COMPLETE
    }

    public override fun onCleared() {
        coroutineScope.cancel()
    }

}
