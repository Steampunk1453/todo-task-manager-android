package org.task.manager.data.network.source

import android.content.Intent
import android.provider.CalendarContract
import org.task.manager.domain.model.CalendarEvent

private const val EVENT_LOCATION = "Home"

class CalendarDataSource {

    fun buildInsertEvent(calendarEvent: CalendarEvent) =
        Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendarEvent.beginTime)
            .putExtra(CalendarContract.Events.TITLE, calendarEvent.title)
            .putExtra(CalendarContract.Events.DESCRIPTION, calendarEvent.description)
            .putExtra(CalendarContract.Events.EVENT_LOCATION, EVENT_LOCATION)

}
