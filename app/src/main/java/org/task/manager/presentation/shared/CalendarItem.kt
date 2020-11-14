package org.task.manager.presentation.shared

import androidx.annotation.ColorRes
import java.time.LocalDateTime

data class CalendarItem(
    val id: Long,
    val title: String,
    val startDate: LocalDateTime,
    val deadline: LocalDateTime,
    val check: Int,
    val itemType: ItemType,
    @ColorRes val color: Int
)

