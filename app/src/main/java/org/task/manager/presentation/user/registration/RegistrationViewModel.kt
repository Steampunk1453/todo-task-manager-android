package org.task.manager.presentation.user.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.domain.usecase.user.RegisterUser
import java.util.Locale

class RegistrationViewModel(private val registerUser: RegisterUser) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val registrationState = MutableLiveData<RegistrationState>(RegistrationState.COLLECT_PROFILE_DATA)

    fun createAccount(username: String, email: String, password: String) {
        val langKey = Locale.getDefault().language
        val registerRequest = RegisterRequest(username, email, password, langKey)
        coroutineScope.launch {
            registerUser.execute(registerRequest)
            registrationState.postValue(RegistrationState.REGISTRATION_COMPLETED)
        }
    }

    fun userCancelledRegistration(): Boolean {
        // Clear existing registration data
        registrationState.value = RegistrationState.COLLECT_PROFILE_DATA
        return true
    }

}