package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.request.PasswordRequest

open class PasswordRequestStub {

    companion object {
        private val faker = Faker()

        fun random(
            currentPassword: String = faker.code.asin(),
            newPassword: String = faker.code.asin()
        ) = PasswordRequest(
            currentPassword,
            newPassword
        )
    }

}
