package org.task.manager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.task.manager.domain.model.Bookshop

@Entity(tableName = "Bookshop")
data class BookshopEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val url: String
)

fun BookshopEntity.toDomain(): Bookshop = Bookshop(id, name, url)

fun Bookshop.toEntity(): BookshopEntity = BookshopEntity(id, name, url)
