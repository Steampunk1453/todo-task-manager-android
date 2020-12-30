package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.Bookshop
import kotlin.random.Random

open class BookshopStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            name: String = faker.company.name(),
            url: String = faker.internet.domain(),
        ) = Bookshop(
            id,
            name,
            url,
        )
    }

}
