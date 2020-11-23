package org.task.manager.presentation.shared

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class CalendarItem(
    val id: Long,
    val title: String,
    val startDate: String,
    val deadline: String,
    val itemType: ItemType,
    @ColorRes val color: Int,
    @DrawableRes val icon: Int,
)

