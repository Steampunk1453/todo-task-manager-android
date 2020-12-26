package org.task.manager.instrumented.integration.stub

import org.task.manager.domain.model.Book
import org.task.manager.domain.model.User
import java.sql.Timestamp
import kotlin.random.Random

open class BookStubIT {

    companion object {

        fun getBook(
                id: Long = Random.nextLong(1, 500),
                title: String = "title",
                author: String = "author",
                genre: String = "genre",
                editorial: String = "editorial",
                editorialUrl: String = "editorialUrl",
                bookshop: String = "bookshop",
                bookshopUrl: String = "bookshopUrl",
                startDate: String = Timestamp(1601126968).toInstant().toString(),
                deadline: String = Timestamp(1601126975).toInstant().toString(),
                check: Int = Random.nextInt(0, 1),
                user: User = User(
                        Random.nextLong(1, 500)
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

        fun getBook1(
                id: Long = Random.nextLong(501, 1000),
                title: String = "title1",
                author: String = "author1",
                genre: String = "genre1",
                editorial: String = "editorial1",
                editorialUrl: String = "editorialUrl1",
                bookshop: String = "bookshop1",
                bookshopUrl: String = "bookshopUrl1",
                startDate: String = Timestamp(1601126968).toInstant().toString(),
                deadline: String = Timestamp(1601126975).toInstant().toString(),
                check: Int = Random.nextInt(0, 1),
                user: User = User(
                        Random.nextLong(501, 1000)
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

        fun getNewBook(bookId: Long?, userId: Long?): Book {
            return Book(
                    id = bookId,
                    title = "newTitle",
                    author = "newAuthor",
                    genre = "newGenre",
                    editorial = "newEditorial",
                    editorialUrl = "newEditorialUrl",
                    bookshop = "newBookshopUrl",
                    bookshopUrl = "newBookshopUrl",
                    startDate = Timestamp(1601126968).toInstant().toString(),
                    deadline = Timestamp(1601126975).toInstant().toString(),
                    check = Random.nextInt(0, 1),
                    user = User(
                            userId
                    )
            )
        }
    }

}


