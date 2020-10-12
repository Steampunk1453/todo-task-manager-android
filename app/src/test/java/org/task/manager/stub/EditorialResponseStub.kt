package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.data.network.model.response.EditorialResponse
import kotlin.random.Random

open class EditorialResponseStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            name: String = faker.book.publisher(),
            url: String = faker.internet.domain(),
        ) = EditorialResponse(
            id,
            name,
            url,
        )
    }

}
