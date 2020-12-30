package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.Genre
import kotlin.random.Random

open class GenreStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            name: String = faker.book.genre(),
            literary: Int = Random.nextInt(0, 1)
        ) = Genre(
            id,
            name,
            literary
        )
    }

}
