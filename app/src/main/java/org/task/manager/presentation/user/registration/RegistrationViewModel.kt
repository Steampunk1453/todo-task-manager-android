package org.task.manager.presentation.user.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.domain.model.User
import org.task.manager.domain.model.state.RegistrationState
import org.task.manager.domain.usecase.user.ActivateUser
import org.task.manager.domain.usecase.user.RegisterUser
import java.util.Locale

class RegistrationViewModel(private val registerUser: RegisterUser,
                            private val activateUser: ActivateUser) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val registrationState = MutableLiveData(RegistrationState.COLLECT_PROFILE_DATA)

    fun createAccount(username: String, email: String, password: String) {
        val langKey = Locale.getDefault().language
        val registeredUser = User(username = username, email = email, password = password,
            langKey = langKey
        )
        coroutineScope.launch {
            val registrationResponse = registerUser.execute(registeredUser)
            registrationState.postValue(registrationResponse)
        }
    }

    fun userCancelledRegistration(): Boolean {
        // Clear existing registration data
        registrationState.value = RegistrationState.COLLECT_PROFILE_DATA
        return true
    }

    fun activateAccount(activateKey: String) {
        coroutineScope.launch {
           val activationResponse = activateUser.execute(activateKey)
            registrationState.postValue(activationResponse)
        }
    }

}