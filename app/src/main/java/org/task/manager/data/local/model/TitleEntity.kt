package org.task.manager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.task.manager.domain.model.Title

@Entity(tableName = "TitleInfo")
data class TitleEntity(
    @PrimaryKey
    val id: String,
    val title: String?,
    val rank: Int?,
    val type: String,
    val genres: String?,
    val platform: String?,
    val website: String?,
)

fun TitleEntity.toDomain(): Title = Title(id, title, rank, type, genres, platform, website)

fun Title.toEntity(): TitleEntity = TitleEntity(id, title, rank, type, genres, platform, website)
