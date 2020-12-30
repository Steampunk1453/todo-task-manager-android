package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.Platform
import kotlin.random.Random

open class PlatformStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            name: String = faker.siliconValley.companies(),
            url: String = faker.internet.domain(),
        ) = Platform(
            id,
            name,
            url,
        )
    }

}
