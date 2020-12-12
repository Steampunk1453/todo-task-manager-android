package org.task.manager.presentation.shared

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import org.task.manager.R
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Book

private const val BLUE_COLOR =  R.color.blue_800
private const val TEAL_COLOR =  R.color.teal_700
private const val VIDEO_ICON =  R.drawable.ic_video_library_24px
private const val BOOK_ICON =  R.drawable.ic_books_library_24dp

data class CalendarItem(
    val title: String,
    val startDate: String,
    val endDate: String,
    val itemType: ItemType,
    val audiovisual: Audiovisual? = null,
    val book: Book? = null,
    @ColorRes val color: Int,
    @DrawableRes val icon: Int,
)

fun Audiovisual.toCalendar(audiovisual: Audiovisual): CalendarItem = CalendarItem(title, startDate,
    deadline, ItemType.AUDIOVISUAL, audiovisual, color = BLUE_COLOR, icon = VIDEO_ICON
)

fun Book.toCalendar(book: Book): CalendarItem = CalendarItem(title, startDate, deadline,
    ItemType.BOOK, book = book, color = TEAL_COLOR, icon = BOOK_ICON
)
