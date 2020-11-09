package org.task.manager.domain.usecase.calendar

import org.task.manager.domain.model.CalendarEvent
import org.task.manager.domain.repository.CalendarRepository

class CreateCalendarEvent(private val repository: CalendarRepository) {
    suspend fun execute(calendarEvent: CalendarEvent) = repository.createEvent(calendarEvent)
}