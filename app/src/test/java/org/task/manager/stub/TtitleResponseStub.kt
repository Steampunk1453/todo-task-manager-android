package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.response.TitleResponse
import kotlin.random.Random

open class TitleResponseStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            name: String = faker.movie.title(),
        ) = TitleResponse(
            id,
            name,
        )
    }

}
