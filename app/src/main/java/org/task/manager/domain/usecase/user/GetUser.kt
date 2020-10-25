package org.task.manager.domain.usecase.user

import org.task.manager.domain.model.User
import org.task.manager.domain.repository.AccountRepository

class GetUser(private val repository: AccountRepository) {

    suspend fun execute(): User {
        return repository.get()
    }

}