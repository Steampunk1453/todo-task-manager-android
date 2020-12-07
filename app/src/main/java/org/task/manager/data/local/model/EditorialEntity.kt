package org.task.manager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.task.manager.domain.model.Editorial

@Entity(tableName = "Editorial")
data class EditorialEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val url: String
)

fun EditorialEntity.toDomain(): Editorial = Editorial(id, name, url)

fun Editorial.toEntity(): EditorialEntity = EditorialEntity(id, name, url)

