package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.User

open class UserStub {

    companion object {
        private val faker = Faker()

        fun random(
            username: String = faker.name.name(),
            email: String = faker.internet.email(),
            password: String = faker.code.asin(),
            langKey: String = "en"
        ) = User(
            email = email,
            username = username,
            password = password,
            langKey = langKey
        )
    }

}
