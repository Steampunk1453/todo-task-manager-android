package org.task.manager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.User

@Entity(tableName = "Audiovisual")
data class AudiovisualEntity(
        @PrimaryKey
        val id: Long?,
        val title: String,
        val genre: String,
        val platform: String,
        val platformUrl: String,
        val startDate: String,
        val deadline: String,
        val check: Int,
        val userId: Long?
)

fun AudiovisualEntity.toDomain(): Audiovisual = Audiovisual(
        id, title, genre, platform, platformUrl,
        startDate, deadline, check, toUserDomain()
)

fun AudiovisualEntity.toUserDomain(): User = User(userId)

fun Audiovisual.toEntity(): AudiovisualEntity = AudiovisualEntity(
        id, title, genre, platform, platformUrl,
        startDate, deadline, check, user?.id
)
