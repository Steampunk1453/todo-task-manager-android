package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.User
import kotlin.random.Random

open class UserStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            username: String = faker.name.name(),
            email: String = faker.internet.email(),
            password: String = faker.code.asin(),
            langKey: String = "en"
        ) = User(
            id = id,
            username = username,
            email = email,
            password = password,
            langKey = langKey
        )
    }

}
