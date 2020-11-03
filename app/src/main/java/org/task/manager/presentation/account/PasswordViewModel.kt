package org.task.manager.presentation.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.PasswordRequest
import org.task.manager.domain.model.state.AccountState
import org.task.manager.domain.usecase.user.password.UpdatePassword

class PasswordViewModel(private val updatePassword: UpdatePassword) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val updatePasswordState = MutableLiveData(AccountState.UPDATING)

    fun updatePassword(currentPassword: String, newPassword: String) {
        val passwordRequest = PasswordRequest(currentPassword, newPassword)
        coroutineScope.launch {
            val updatePasswordResponse = updatePassword.execute(passwordRequest)
            updatePasswordState.postValue(updatePasswordResponse)
        }
    }

}