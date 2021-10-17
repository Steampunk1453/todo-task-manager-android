package org.task.manager.domain.model

data class Title(
    val id: String,
    val title: String?,
    val rank: Int?,
    val type: String,
    val genres: String?,
    val platform: String?,
    val website: String?
)


