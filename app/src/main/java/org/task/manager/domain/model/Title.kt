package org.task.manager.domain.model

data class Title(
    val id: Long,
    val title: String,
    val type: String,
    val genres: String,
    val platform: String?,
    val website: String?
)


