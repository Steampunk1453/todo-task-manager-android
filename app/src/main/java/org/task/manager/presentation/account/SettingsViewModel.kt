package org.task.manager.presentation.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.domain.model.User
import org.task.manager.domain.model.state.AccountState
import org.task.manager.domain.usecase.user.GetUser
import org.task.manager.domain.usecase.user.UpdateUser
import java.util.Locale

class SettingsViewModel(private val getUser: GetUser,
                        private val updateUser: UpdateUser) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val user = MutableLiveData<User>()
    val updateAccountState = MutableLiveData(AccountState.UPDATING)

    fun getAccount() {
        coroutineScope.launch {
            val userResult = getUser.execute()
            user.postValue(userResult)
        }
    }

    fun updateAccount(username: String, email: String, firstName: String, lastName: String) {
        val langKey = Locale.getDefault().language
        val updatedUser = User(username = username, email = email, langKey = langKey,
            firstName = firstName, lastName = lastName
        )
        coroutineScope.launch {
            val updateAccountResponse = updateUser.execute(updatedUser)
            updateAccountState.postValue(updateAccountResponse)
        }
    }

}