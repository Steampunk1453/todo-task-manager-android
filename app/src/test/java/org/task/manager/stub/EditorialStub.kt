package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.Editorial
import kotlin.random.Random

open class EditorialStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            name: String = faker.book.publisher(),
            url: String = faker.internet.domain(),
        ) = Editorial(
            id,
            name,
            url,
        )
    }

}
