package org.task.manager.domain.usecase.user.password

import org.task.manager.data.network.model.request.ResetPasswordRequest
import org.task.manager.domain.Result
import org.task.manager.domain.model.state.AccountState
import org.task.manager.domain.repository.AccountRepository
import timber.log.Timber

class FinishResetPassword(private val repository: AccountRepository) {

    suspend fun execute(request: ResetPasswordRequest): AccountState? {
        return when (val result = repository.finishPasswordReset(request)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailedResponse(it)
            }
        }
    }

    private fun manageSuccessResponse(response: String): AccountState {
        Timber.d("Successful reset user password: %s", response)
        return AccountState.UPDATE_COMPLETED
    }

    private fun manageFailedResponse(error: String): AccountState {
        Timber.e("Invalid reset user password: %s", error)
        return AccountState.INVALID_UPDATE
    }

}