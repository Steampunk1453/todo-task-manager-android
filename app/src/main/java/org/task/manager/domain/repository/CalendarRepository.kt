package org.task.manager.domain.repository

import android.content.Intent
import org.task.manager.domain.model.CalendarEvent

interface CalendarRepository {
    suspend fun createEvent(calendarEvent: CalendarEvent): Intent
}