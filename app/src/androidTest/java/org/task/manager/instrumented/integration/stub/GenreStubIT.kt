package org.task.manager.instrumented.integration.stub

import org.task.manager.domain.model.Genre
import kotlin.random.Random

open class GenreStubIT {

    companion object {
            fun getGenre(
                    id: Long = Random.nextLong(1, 500),
                    name: String = "name",
                    isLiterary: Int = 1

            ) = Genre(
                    id,
                    name,
                    isLiterary
            )

            fun getGenre1(
                    id: Long = Random.nextLong(501, 1000),
                    name: String = "name1",
                    isLiterary: Int = 0

            ) = Genre(
                    id,
                    name,
                    isLiterary
            )
    }

}


