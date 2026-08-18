package com.regisoc.modules.events.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.events.domain.EventRegistration
import com.regisoc.modules.events.domain.EventRegistrationRepository
import com.regisoc.modules.events.domain.EventRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import com.regisoc.modules.events.domain.EventStatus

data class RegisterClubCommand(
    val eventId: Long,
    val clubId: Long
)

@Service
class RegisterClubToEventUseCase(
    private val eventRepository: EventRepository,
    private val clubRepository: ClubRepository,
    private val registrationRepository: EventRegistrationRepository
) {
    fun execute(command: RegisterClubCommand): EventRegistration {
        val event = eventRepository.findById(command.eventId)
            .orElseThrow { EntityNotFoundException("El evento no existe con id: ${command.eventId}") }

        require(event.status == EventStatus.UPCOMING) {
            "Ya no se puede registrar al club por el estado del evento"
        }

        val club = clubRepository.findActiveById(command.clubId)
            .orElseThrow { EntityNotFoundException("Club not found with id: ${command.clubId}") }

        if (registrationRepository.findByEventIdAndClubId(command.eventId, command.clubId).isPresent) {
            throw IllegalStateException("Club ${command.clubId} is already registered for event ${command.eventId}")
        }

        val registration = EventRegistration(
            event = event,
            club = club
        )
        return registrationRepository.save(registration)
    }
}
