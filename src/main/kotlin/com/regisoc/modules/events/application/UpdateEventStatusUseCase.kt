package com.regisoc.modules.events.application

import com.regisoc.modules.events.domain.EventRepository
import com.regisoc.modules.events.domain.EventStatus
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

data class UpdateEventStatusCommand(
    val eventId: Long,
    val status: EventStatus
)

@Service
class UpdateEventStatusUseCase(
    private val repository: EventRepository
) {
    fun execute(command: UpdateEventStatusCommand) {
        val event = repository.findById(command.eventId)
            .orElseThrow { EntityNotFoundException("Event not found with id: ${command.eventId}") }

        val current = event.status
        val target = command.status

        when (target) {
            EventStatus.ONGOING ->
                require(current == EventStatus.UPCOMING) {
                    "Cannot start event ${event.id}: current status is $current"
                }
            EventStatus.FINISHED ->
                require(current == EventStatus.ONGOING) {
                    "Cannot finish event ${event.id}: current status is $current"
                }
            EventStatus.UPCOMING ->
                throw IllegalArgumentException("Cannot set event ${event.id} back to UPCOMING")
        }

        event.changeStatus(target)
        repository.save(event)
    }
}