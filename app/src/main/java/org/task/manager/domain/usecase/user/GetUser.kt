package org.task.manager.domain.usecase.user

import org.task.manager.domain.Result
import org.task.manager.domain.model.User
import org.task.manager.domain.repository.AccountRepository
import timber.log.Timber

class GetUser(private val repository: AccountRepository) {

    suspend fun execute(): User? {
        return when (val result = repository.get()) {
            is Result.Success -> handleSuccessResult(result.data)
            is Result.Error -> result.throwable.message?.let {
                handleFailedResult(it)
            }
        }
    }

    private fun handleSuccessResult(result: User): User {
        Timber.d("Successful get user: %s", result)
        return result
    }

    private fun handleFailedResult(error: String): User {
        Timber.e("Invalid get user: %s", error)
        return User()
    }

}