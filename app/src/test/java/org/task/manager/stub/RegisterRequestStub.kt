package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.request.RegisterRequest

open class RegisterRequestStub {

    companion object {
        private val faker = Faker()

        fun random(
            username: String = faker.name.name(),
            email: String = faker.internet.email(),
            password: String = faker.code.asin(),
            langKey: String = "en"
        ) = RegisterRequest(
            email,
            username,
            password,
            langKey
        )
    }

}
