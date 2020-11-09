package org.task.manager.data.repository

import android.content.Intent
import org.task.manager.data.network.source.CalendarDataSource
import org.task.manager.domain.model.CalendarEvent
import org.task.manager.domain.repository.CalendarRepository

class DefaultCalendarRepository(private val dataSource: CalendarDataSource) : CalendarRepository {
    override suspend fun createEvent(calendarEvent: CalendarEvent): Intent {
        return  dataSource.buildInsertEvent(calendarEvent)
    }
}