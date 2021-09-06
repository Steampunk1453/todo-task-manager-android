package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.Title

open class TitleStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: String = faker.movie.title(),
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
