package org.task.manager.presentation.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.domain.usecase.RegisterUser

class RegistrationViewModel(private val registerUser: RegisterUser) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    enum class RegistrationState {
        COLLECT_PROFILE_DATA,
        REGISTRATION_COMPLETED
    }

    val registrationState = MutableLiveData<RegistrationState>(RegistrationState.COLLECT_PROFILE_DATA)

    fun createAccount(username: String, email: String, password: String) {
        val registerRequest = RegisterRequest(username, email, password)
        coroutineScope.launch {
            registerUser.execute(registerRequest)
            registrationState.postValue(RegistrationState.REGISTRATION_COMPLETED)
        }
    }

    fun userCancelledRegistration(): Boolean {
        // Clear existing registration data
        registrationState.value = RegistrationState.COLLECT_PROFILE_DATA
//        authToken = ""
        return true
    }

}