package org.task.manager.domain.usecase.user.password

import org.task.manager.data.network.model.request.PasswordRequest
import org.task.manager.domain.Result
import org.task.manager.domain.model.state.AccountState
import org.task.manager.domain.repository.AccountRepository
import timber.log.Timber

class UpdatePassword(private val repository: AccountRepository) {

    suspend fun execute(request: PasswordRequest): AccountState? {
        return when (val result = repository.changePassword(request)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailedResponse(it)
            }
        }
    }

    private fun manageSuccessResponse(response: String): AccountState {
        Timber.d("Successful update user password: %s", response)
        return AccountState.UPDATE_COMPLETED
    }

    private fun manageFailedResponse(error: String): AccountState {
        Timber.e("Invalid update user password: %s", error)
        return AccountState.INVALID_UPDATE
    }

}