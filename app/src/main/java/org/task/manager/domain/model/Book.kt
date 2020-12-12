package org.task.manager.domain.model

data class Book(
    val id: Long?,
    val title: String,
    val author: String,
    val genre: String,
    val editorial: String,
    val editorialUrl: String,
    val bookshop: String,
    val bookshopUrl: String,
    val startDate: String,
    val deadline: String,
    val check: Int,
    val user: User?
)

