package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.request.LoginRequest
import kotlin.random.Random

open class LoginRequestStub {

    companion object {
        private val faker = Faker()

        fun random(
            username: String = faker.name.name(),
            password: String = faker.code.asin(),
            isRememberMe: Boolean = Random.nextBoolean()
        ) = LoginRequest(
            username,
            password,
            isRememberMe
        )
    }

}
