package org.task.manager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.task.manager.domain.model.Genre

@Entity(tableName = "Genre")
data class GenreEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val isLiterary: Int
)

fun GenreEntity.toDomain(): Genre = Genre(id, name, isLiterary)

fun Genre.toEntity(): GenreEntity = GenreEntity(id, name, isLiterary)
