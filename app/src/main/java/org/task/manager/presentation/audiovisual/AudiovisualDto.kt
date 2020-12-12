package org.task.manager.presentation.audiovisual

import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.User

data class AudiovisualDto(
    val id: Long? = null,
    val title: String,
    val genre: String,
    val platform: String,
    val platformUrl: String,
    val startDate: String,
    val deadline: String,
    val check: Int,
    val userId: Long? = null,
)

fun AudiovisualDto.toDomain(): Audiovisual = Audiovisual(id, title, genre, platform, platformUrl,
    startDate, deadline, check, toUserDomain()
)

fun AudiovisualDto.toUserDomain(): User = User(userId)