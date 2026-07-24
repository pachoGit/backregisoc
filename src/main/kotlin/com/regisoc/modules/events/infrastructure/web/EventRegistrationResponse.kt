package com.regisoc.modules.events.infrastructure.web

import com.regisoc.modules.events.domain.EventRegistration
import java.time.LocalDateTime

data class EventRegistrationResponse(
    val id: Long,
    val eventId: Long,
    val clubId: Long,
    val registeredAt: LocalDateTime
) {
    companion object {
        fun from(registration: EventRegistration) = EventRegistrationResponse(
            id = registration.id,
            eventId = registration.event.id,
            clubId = registration.club.id,
            registeredAt = registration.registeredAt
        )
    }
}
