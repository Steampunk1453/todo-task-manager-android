package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.Title
import kotlin.random.Random

open class TitleStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            title: String = faker.movie.title(),
        ) = Title(
            id,
            title,
            title,
            title,
            title,
            title
        )
    }

}
