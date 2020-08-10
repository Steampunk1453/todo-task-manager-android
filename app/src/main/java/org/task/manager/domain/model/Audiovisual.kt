package org.task.manager.domain.model

data class Audiovisual (
    val id: Long,
    val title: String,
    val genre: String,
    val platform: String,
    val platformUrl: String,
    val startDate: String,
    val deadline: String,
    val check: Int
)
