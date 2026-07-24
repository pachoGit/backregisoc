package com.regisoc.modules.matchdates.infrastructure.web

import com.regisoc.modules.matchdates.application.CreateMatchDateCommand
import com.regisoc.modules.matchdates.application.CreateMatchDateUseCase
import com.regisoc.modules.matchdates.application.GetMatchDatesUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/match-dates")
class MatchDateController(
    private val createMatchDateUseCase: CreateMatchDateUseCase,
    private val getMatchDatesUseCase: GetMatchDatesUseCase
) {
    @PostMapping
    fun create(@Valid @RequestBody request: CreateMatchDateRequest): ResponseEntity<MatchDateResponse> {
        val command = CreateMatchDateCommand(
            eventId = request.eventId,
            name = request.name,
            date = request.date
        )
        val matchDate = createMatchDateUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(MatchDateResponse.from(matchDate))
    }

    @GetMapping("/by-event/{eventId}")
    fun getByEvent(@PathVariable eventId: Long): ResponseEntity<List<MatchDateResponse>> {
        val matchDates = getMatchDatesUseCase.findByEvent(eventId)
        return ResponseEntity.ok(matchDates.map { MatchDateResponse.from(it) })
    }
}
