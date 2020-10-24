package org.task.manager.domain.usecase.user

import org.task.manager.data.network.model.request.UserRequest
import org.task.manager.domain.Result
import org.task.manager.domain.model.state.AccountState
import org.task.manager.domain.repository.AccountRepository
import timber.log.Timber

class UpdaterUser(private val repository: AccountRepository) {

    suspend fun execute(request: UserRequest): AccountState? {
        return when (val result = repository.save(request)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailedResponse(it)
            }
        }
    }

    private fun manageSuccessResponse(response: String): AccountState {
        Timber.d("Successful update user data: %s", response)
        return AccountState.UPDATE_COMPLETED
    }

    private fun manageFailedResponse(error: String): AccountState {
        Timber.e("Invalid update user data: %s", error)
        return AccountState.INVALID_UPDATE
    }

}