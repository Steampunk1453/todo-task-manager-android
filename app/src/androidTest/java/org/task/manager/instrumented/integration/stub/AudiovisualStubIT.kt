package org.task.manager.instrumented.integration.stub

import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.User
import java.sql.Timestamp
import kotlin.random.Random

open class AudiovisualStubIT {

    companion object {

        fun getAudiovisual(
                id: Long = Random.nextLong(1, 500),
                title: String = "title",
                genre: String = "genre",
                platform: String = "platform",
                platformUrl: String = "platformUrl",
                startDate: String = Timestamp(1601126968).toInstant().toString(),
                deadline: String = Timestamp(1601126975).toInstant().toString(),
                check: Int = Random.nextInt(0, 1),
                user: User = User(
                        Random.nextLong(1, 500)
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

        fun getAudiovisual1(
                id: Long = Random.nextLong(501, 1000),
                title: String = "title1",
                genre: String = "genre1",
                platform: String = "platform1",
                platformUrl: String = "platformUrl1",
                startDate: String = Timestamp(1601126968).toInstant().toString(),
                deadline: String = Timestamp(1601126975).toInstant().toString(),
                check: Int = Random.nextInt(0, 1),
                user: User = User(
                        Random.nextLong(501, 1000)
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

        fun getNewAudiovisual(audiovisualId: Long?, userId: Long?): Audiovisual {
            return Audiovisual(id = audiovisualId,
                    title = "newTitle",
                    genre = "newGenre",
                    platform = "newPlatform",
                    platformUrl = "newPlatformUrl",
                    startDate = Timestamp(1601126968).toInstant().toString(),
                    deadline = Timestamp(1601126975).toInstant().toString(),
                    check = Random.nextInt(0, 1),
                    user = User(userId
                    )
            )
        }
    }

}


