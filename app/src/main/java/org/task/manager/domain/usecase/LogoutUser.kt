package org.task.manager.domain.usecase

import org.task.manager.shared.service.SessionManagerService

class LogoutUser(private val sessionManagerService: SessionManagerService) {

    fun execute() {
        sessionManagerService.removeAuthToken()
    }
}