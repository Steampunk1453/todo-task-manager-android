package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.response.UserResponse
import kotlin.random.Random

open class UserResponseStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            username: String = faker.name.name(),
            email: String = faker.internet.email(),
            firstName: String = faker.name.firstName(),
            lastName: String = faker.name.lastName(),
        ) = UserResponse(
            id,
            username,
            email,
            firstName,
            lastName
        )
    }

}
