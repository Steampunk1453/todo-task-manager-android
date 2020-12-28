package org.task.manager.instrumented.integration.stub

import org.task.manager.domain.model.Platform
import kotlin.random.Random

open class PlatformStubIT {

    companion object {
            fun getPlatform(
                    id: Long = Random.nextLong(1, 500),
                    name: String = "name",
                    url: String = "url"

            ) = Platform(
                    id,
                    name,
                    url
            )

            fun getPlatform1(
                    id: Long = Random.nextLong(501, 1000),
                    name: String = "name1",
                    url: String = "url1"

            ) = Platform(
                    id,
                    name,
                    url
            )
    }

}


