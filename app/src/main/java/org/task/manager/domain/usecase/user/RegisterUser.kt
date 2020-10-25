package org.task.manager.domain.usecase.user

import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.domain.Result
import org.task.manager.domain.model.state.RegistrationState
import org.task.manager.domain.repository.AccountRepository
import timber.log.Timber

class RegisterUser(private val repository: AccountRepository) {

    suspend fun execute(request: RegisterRequest): RegistrationState?  {
       return when (val result = repository.register(request)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailedResponse(it)
            }
        }
    }

    private fun manageSuccessResponse(response: String): RegistrationState {
        Timber.d("Successful register: %s", response)
        return RegistrationState.REGISTRATION_COMPLETED
    }

    private fun manageFailedResponse(error: String): RegistrationState {
        Timber.e("Invalid Register: %s", error)
        return RegistrationState.INVALID_REGISTRATION
    }

}