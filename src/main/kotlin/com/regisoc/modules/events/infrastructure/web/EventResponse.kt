package com.regisoc.modules.events.infrastructure.web

import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.events.domain.EventStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class EventResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val status: EventStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(event: Event) = EventResponse(
            id = event.id,
            name = event.name,
            description = event.description,
            startDate = event.startDate,
            endDate = event.endDate,
            status = event.status,
            createdAt = event.createdAt,
            updatedAt = event.updatedAt
        )
    }
}
