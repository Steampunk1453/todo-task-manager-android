package org.task.manager.domain.usecase.user

import org.task.manager.domain.Result
import org.task.manager.domain.model.state.RegistrationState
import org.task.manager.domain.repository.AccountRepository
import timber.log.Timber

class ActivateUser(private val repository: AccountRepository) {

    suspend fun execute(activateKey: String): RegistrationState? {
        return when (val result = repository.activate(activateKey)) {
            is Result.Success -> handleSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                handleFailedResponse(it)
            }
        }
    }

    private fun handleSuccessResponse(response: String): RegistrationState {
        Timber.d("Successful activation: %s", response)
        return RegistrationState.ACTIVATION_COMPLETED
    }

    private fun handleFailedResponse(error: String): RegistrationState {
        Timber.e("Invalid activation: %s", error)
        return RegistrationState.INVALID_ACTIVATION
    }

}