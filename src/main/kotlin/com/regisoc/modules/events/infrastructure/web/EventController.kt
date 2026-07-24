package com.regisoc.modules.events.infrastructure.web

import com.regisoc.modules.events.application.CreateEventCommand
import com.regisoc.modules.events.application.CreateEventUseCase
import com.regisoc.modules.events.application.GetEventUseCase
import com.regisoc.modules.events.application.RegisterClubCommand
import com.regisoc.modules.events.application.RegisterClubToEventUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/events")
class EventController(
    private val createEventUseCase: CreateEventUseCase,
    private val registerClubToEventUseCase: RegisterClubToEventUseCase,
    private val getEventUseCase: GetEventUseCase
) {
    @PostMapping
    fun create(@Valid @RequestBody request: CreateEventRequest): ResponseEntity<EventResponse> {
        val command = CreateEventCommand(
            name = request.name,
            description = request.description,
            startDate = request.startDate,
            endDate = request.endDate
        )
        val event = createEventUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(EventResponse.from(event))
    }

    @PostMapping("/{eventId}/registrations")
    fun registerClub(
        @PathVariable eventId: Long,
        @Valid @RequestBody request: RegisterClubRequest
    ): ResponseEntity<EventRegistrationResponse> {
        val command = RegisterClubCommand(eventId = eventId, clubId = request.clubId)
        val registration = registerClubToEventUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(EventRegistrationResponse.from(registration))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<EventResponse> {
        val event = getEventUseCase.findById(id)
        return ResponseEntity.ok(EventResponse.from(event))
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<EventResponse>> {
        val events = getEventUseCase.findAll()
        return ResponseEntity.ok(events.map { EventResponse.from(it) })
    }

    @GetMapping("/{eventId}/registrations")
    fun getRegistrations(@PathVariable eventId: Long): ResponseEntity<List<EventRegistrationResponse>> {
        val registrations = getEventUseCase.getRegistrations(eventId)
        return ResponseEntity.ok(registrations.map { EventRegistrationResponse.from(it) })
    }
}
