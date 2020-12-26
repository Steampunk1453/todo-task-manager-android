package org.task.manager.instrumented.integration.stub

import org.task.manager.domain.model.Book
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.model.User
import java.sql.Timestamp
import kotlin.random.Random

open class BookshopStubIT {

    companion object {
            fun getBookshop(
                    id: Long = Random.nextLong(1, 500),
                    name: String = "name",
                    url: String = "url"

            ) = Bookshop(
                    id,
                    name,
                    url
            )

            fun getBookshop1(
                    id: Long = Random.nextLong(501, 1000),
                    name: String = "name1",
                    url: String = "url1"

            ) = Bookshop(
                    id,
                    name,
                    url
            )
    }

}


