package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.response.GenreResponse
import kotlin.random.Random

open class GenreResponseStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            name: String = faker.book.genre(),
            literary: Int = Random.nextInt(0, 1)
        ) = GenreResponse(
            id,
            name,
            literary
        )
    }

}
