package org.task.manager.presentation.account.password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.PasswordRequest
import org.task.manager.data.network.model.request.ResetPasswordRequest
import org.task.manager.domain.model.state.AccountState
import org.task.manager.domain.usecase.user.password.FinishResetPassword
import org.task.manager.domain.usecase.user.password.StartResetPassword
import org.task.manager.domain.usecase.user.password.UpdatePassword

class PasswordViewModel(private val updatePassword: UpdatePassword,
                        private val startResetPassword: StartResetPassword,
                        private val finishResetPassword: FinishResetPassword) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val updatePasswordState = MutableLiveData(AccountState.UPDATING)
    val startResetPasswordState = MutableLiveData(AccountState.UPDATING)
    val finishResetPasswordState = MutableLiveData(AccountState.UPDATING)

    fun updatePassword(currentPassword: String, newPassword: String) {
        val passwordRequest = PasswordRequest(currentPassword, newPassword)
        coroutineScope.launch {
            val updatePasswordResponse = updatePassword.execute(passwordRequest)
            updatePasswordState.postValue(updatePasswordResponse)
        }
    }

    fun startResetPassword(email: String) {
        coroutineScope.launch {
            val startResetPasswordResponse = startResetPassword.execute(email)
            startResetPasswordState.postValue(startResetPasswordResponse)
        }
    }

    fun finishResetPassword(key: String, newPassword: String) {
        val resetPasswordRequest = ResetPasswordRequest(key, newPassword)
        coroutineScope.launch {
            val resetPasswordRequestResponse = finishResetPassword.execute(resetPasswordRequest)
            finishResetPasswordState.postValue(resetPasswordRequestResponse)
        }
    }

}