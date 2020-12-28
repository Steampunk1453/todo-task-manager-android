package org.task.manager.instrumented.integration.stub

import org.task.manager.domain.model.Title
import kotlin.random.Random

open class TitleStubIT {

    companion object {
            fun getTitle(
                    id: Long = Random.nextLong(1, 500),
                    name: String = "name"
            ) = Title(
                    id,
                    name,
            )

            fun getTitle1(
                    id: Long = Random.nextLong(501, 1000),
                    name: String = "name1",
            ) = Title(
                    id,
                    name,
            )
    }

}


