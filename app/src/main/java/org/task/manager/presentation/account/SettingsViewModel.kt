package org.task.manager.presentation.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.UserRequest
import org.task.manager.domain.model.User
import org.task.manager.domain.model.state.AccountState
import org.task.manager.domain.usecase.user.GetUser
import org.task.manager.domain.usecase.user.UpdaterUser
import java.util.Locale

class SettingsViewModel(private val getUser: GetUser,
                        private val updaterUser: UpdaterUser) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val user = MutableLiveData<User>()
    val updateAccountState = MutableLiveData(AccountState.UPDATING)

    fun getAccount() {
        coroutineScope.launch {
            val userResult = getUser.execute()
            user.postValue(userResult)
        }
    }

    fun updateAccount(firstName: String, lastName: String, email: String) {
        val langKey = Locale.getDefault().language
        val userRequest = UserRequest(firstName, lastName, email, langKey)
        coroutineScope.launch {
            val updateAccountResponse = updaterUser.execute(userRequest)
            updateAccountState.postValue(updateAccountResponse)
        }

    }

}