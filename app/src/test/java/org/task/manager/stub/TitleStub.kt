package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.Title
import kotlin.random.Random

open class TitleStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: String = faker.movie.quote(),
            title: String = faker.movie.title(),
            rank: Int = Random.nextInt(1, 200),
            type: String = faker.movie.title(),
            genres: String = faker.name.name(),
            platform: String = faker.siliconValley.companies(),
            website: String = faker.internet.domain(),
        ) = Title(
            id,
            title,
            rank,
            type,
            genres,
            platform,
            website
        )

    }

}
