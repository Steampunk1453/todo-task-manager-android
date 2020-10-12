package org.task.manager.stub

import io.github.serpro69.kfaker.Faker
import org.task.manager.domain.model.Book
import org.task.manager.domain.model.User
import java.sql.Timestamp
import kotlin.random.Random

open class BookStub {

    companion object {
        private val faker = Faker()

        fun random(
            id: Long = Random.nextLong(1, 5000),
            title: String = faker.book.title(),
            author: String = faker.book.author(),
            genre: String = faker.book.genre(),
            editorial: String = faker.book.publisher(),
            editorialUrl: String = faker.internet.domain(),
            bookshop: String = faker.company.name(),
            bookshopUrl: String = faker.internet.domain(),
            startDate: String = Timestamp(1601126968).toInstant().toString(),
            deadline: String = Timestamp(1601126975).toInstant().toString(),
            check: Int = Random.nextInt(0, 1),
            user: User = User(
                Random.nextLong(1, 5000),
                faker.name.firstName(),
                faker.internet.email()
            )
        ) = Book(
            id,
            title,
            author,
            genre,
            editorial,
            editorialUrl,
            bookshop,
            bookshopUrl,
            startDate,
            deadline,
            check,
            user
        )
    }

}

