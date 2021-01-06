package org.task.manager.presentation.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.domain.model.state.AuthenticationState
import org.task.manager.domain.usecase.user.LoginUser
import org.task.manager.domain.usecase.user.LogoutUser

class LoginViewModel(
    private val loginUser: LoginUser,
    private val logoutUser: LogoutUser
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val authenticationState = MutableLiveData<AuthenticationState>()
    val logoutState = MutableLiveData<LogoutState>()

    fun refuseAuthentication() {
        authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
    }

    fun authenticate(username: String, password: String, isRemember: Boolean) {
        val loginRequest = LoginRequest(username, password, isRemember)
        coroutineScope.launch {
            val authenticationResponse = loginUser.execute(loginRequest)
            authenticationState.postValue(authenticationResponse)
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
