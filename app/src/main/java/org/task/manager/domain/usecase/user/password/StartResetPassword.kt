package org.task.manager.domain.usecase.user.password

import org.task.manager.domain.Result
import org.task.manager.domain.model.state.AccountState
import org.task.manager.domain.repository.AccountRepository
import timber.log.Timber

class StartResetPassword(private val repository: AccountRepository) {

    suspend fun execute(mail: String): AccountState? {
        return when (val result = repository.requestPasswordReset(mail)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailedResponse(it)
            }
        }
    }

    private fun manageSuccessResponse(response: String): AccountState {
        Timber.d("Successful start to reset user password with email: %s", response)
        return AccountState.UPDATE_COMPLETED
    }

    private fun manageFailedResponse(error: String): AccountState {
        Timber.e("Invalid start to reset user password with email: %s", error)
        return AccountState.INVALID_UPDATE
    }

}