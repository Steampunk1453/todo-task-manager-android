package org.task.manager.instrumented.integration.stub

import org.task.manager.domain.model.Title
import kotlin.random.Random

open class TitleStubIT {

    companion object {

        fun getTitle(
            id: String = "id",
            title: String = "title",
            rank: Int = Random.nextInt(1, 200),
            type: String = "type",
            genres: String = "genres",
            platform: String = "platform",
            website: String = "website",
        ) = Title(
            id,
            title,
            rank,
            type,
            genres,
            platform,
            website
        )

        fun getTitle1(
            id: String = "id1",
            title: String = "title1",
            rank: Int = Random.nextInt(1, 200),
            type: String = "type1",
            genres: String = "genres1",
            platform: String = "platform1",
            website: String = "website1",
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


