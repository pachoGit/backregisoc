package com.regisoc.modules.events.application

import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.events.domain.EventRegistration
import com.regisoc.modules.events.domain.EventRegistrationRepository
import com.regisoc.modules.events.domain.EventRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetEventUseCase(
    private val eventRepository: EventRepository,
    private val registrationRepository: EventRegistrationRepository
) {
    fun findById(id: Long): Event {
        return eventRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Event not found with id: $id") }
    }

    fun findAll(): List<Event> = eventRepository.findAll()

    fun findActive(clubId: Long?): List<Event> = eventRepository.findActive(clubId)

    fun getRegistrations(eventId: Long): List<EventRegistration> =
        registrationRepository.findByEventId(eventId)

    fun getClubRegistrations(clubId: Long): List<EventRegistration> =
        registrationRepository.findByClubId(clubId)
}
