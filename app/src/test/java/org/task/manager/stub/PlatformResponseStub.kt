package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.response.PlatformResponse
import kotlin.random.Random

open class PlatformResponseStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            name: String = faker.siliconValley.companies(),
            url: String = faker.internet.domain(),
        ) = PlatformResponse(
            id,
            name,
            url,
        )
    }

}
