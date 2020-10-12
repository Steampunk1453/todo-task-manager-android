package org.task.manager.domain.usecase.user

import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.domain.Result
import org.task.manager.domain.repository.RegisterRepository
import timber.log.Timber

class RegisterUser(private val repository: RegisterRepository) {

    suspend fun execute(request: RegisterRequest) {
        when (val result = repository.register(request)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailedResponse(it)
            }
        }
    }

    private fun manageSuccessResponse(response: String) {
        Timber.d("Successful register: %s", response)
    }

    private fun manageFailedResponse(error: String) {
        Timber.e("Invalid Register: %s", error)
    }


}