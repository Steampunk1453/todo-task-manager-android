package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.request.ResetPasswordRequest

open class ResetPasswordRequestStub {

    companion object {
        private val faker = Faker()

        fun random(
            key: String = RandomGenerator.getString(),
            newPassword: String = faker.code.asin()
        ) = ResetPasswordRequest(
            key,
            newPassword
        )
    }

}
