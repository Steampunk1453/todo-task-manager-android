package org.task.manager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.task.manager.domain.model.Platform

@Entity(tableName = "Platform")
data class PlatformEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val url: String
)

fun PlatformEntity.toDomain(): Platform = Platform(id, name, url)

fun Platform.toEntity(): PlatformEntity = PlatformEntity(id, name, url)
