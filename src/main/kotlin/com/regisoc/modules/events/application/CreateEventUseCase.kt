package com.regisoc.modules.events.application

import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.events.domain.EventRepository
import com.regisoc.modules.events.domain.EventStatus
import org.springframework.stereotype.Service
import java.time.LocalDate

data class CreateEventCommand(
    val name: String,
    val location: String?,
    val description: String?,
    val startDate: LocalDate,
    val endDate: LocalDate
)

@Service
class CreateEventUseCase(private val repository: EventRepository) {
    fun execute(command: CreateEventCommand): Event {
        val event = Event(
            name = command.name,
            location = command.location,
            description = command.description,
            startDate = command.startDate,
            endDate = command.endDate,
            status = EventStatus.UPCOMING
        )
        return repository.save(event)
    }
}
