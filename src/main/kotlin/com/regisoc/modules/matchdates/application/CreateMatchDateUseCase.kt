package com.regisoc.modules.matchdates.application

import com.regisoc.modules.events.domain.EventRepository
import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.matchdates.domain.MatchDateRepository
import com.regisoc.modules.matchdates.domain.MatchDateStatus
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

data class CreateMatchDateCommand(
    val eventId: Long,
    val name: String,
    val date: LocalDate,
    val status: MatchDateStatus = MatchDateStatus.UPCOMING
)

@Service
class CreateMatchDateUseCase(
    private val repository: MatchDateRepository,
    private val eventRepository: EventRepository
) {
    fun execute(command: CreateMatchDateCommand): MatchDate {
        val event = eventRepository.findById(command.eventId)
            .orElseThrow { EntityNotFoundException("Event not found: ${command.eventId}") }

        val matchDate = MatchDate(
            event = event,
            name = command.name,
            date = command.date,
            status = command.status
        )
        return repository.save(matchDate)
    }
}
