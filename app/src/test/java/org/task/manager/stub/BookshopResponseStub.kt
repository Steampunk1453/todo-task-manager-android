package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.response.BookshopResponse
import kotlin.random.Random

open class BookshopResponseStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            name: String = faker.company.name(),
            url: String = faker.internet.domain(),
        ) = BookshopResponse(
            id,
            name,
            url,
        )
    }

}
