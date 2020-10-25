package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.User
import java.sql.Timestamp
import kotlin.random.Random

open class AudiovisualStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            title: String = faker.movie.title(),
            genre: String = faker.book.genre(),
            platform: String = faker.siliconValley.companies(),
            platformUrl: String = faker.internet.domain(),
            startDate: String = Timestamp(1601126968).toInstant().toString(),
            deadline: String = Timestamp(1601126975).toInstant().toString(),
            check: Int = Random.nextInt(0, 1),
            user: User = User(
                Random.nextLong(1, 5000),
                faker.name.name(),
                faker.internet.email(),
                faker.name.firstName(),
                faker.name.lastName(),

            )
        ) = Audiovisual(
            id,
            title,
            genre,
            platform,
            platformUrl,
            startDate,
            deadline,
            check,
            user
        )
    }

}

