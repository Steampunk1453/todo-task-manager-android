package org.task.manager.instrumented.integration.stub

import org.task.manager.domain.model.Editorial
import kotlin.random.Random

open class EditorialStubIT {

    companion object {
            fun getEditorial(
                    id: Long = Random.nextLong(1, 500),
                    name: String = "name",
                    url: String = "url"

            ) = Editorial(
                    id,
                    name,
                    url
            )

            fun getEditorial1(
                    id: Long = Random.nextLong(501, 1000),
                    name: String = "name1",
                    url: String = "url1"

            ) = Editorial(
                    id,
                    name,
                    url
            )
    }

}


