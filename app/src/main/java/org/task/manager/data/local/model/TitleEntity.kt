package org.task.manager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.task.manager.domain.model.Title

@Entity(tableName = "Title")
data class TitleEntity(
    @PrimaryKey
    val id: Long,
    val name: String
)

fun TitleEntity.toDomain(): Title = Title(id, name)

fun Title.toEntity(): TitleEntity = TitleEntity(id, name)
