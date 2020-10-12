package org.task.manager.domain.usecase.user

import org.task.manager.domain.Result
import org.task.manager.domain.model.RegistrationState
import org.task.manager.domain.repository.RegisterRepository
import timber.log.Timber

class ActivateUser(private val repository: RegisterRepository) {

    suspend fun execute(activateKey: String): RegistrationState? {
        return when (val result = repository.activate(activateKey)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailedResponse(it)
            }
        }
    }

    private fun manageSuccessResponse(response: String): RegistrationState {
        Timber.d("Successful activation: %s", response)
        return RegistrationState.ACTIVATION_COMPLETED
    }

    private fun manageFailedResponse(error: String): RegistrationState {
        Timber.e("Invalid activation: %s", error)
        return RegistrationState.INVALID_ACTIVATION
    }


}