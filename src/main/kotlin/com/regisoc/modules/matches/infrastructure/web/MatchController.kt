package com.regisoc.modules.matches.infrastructure.web

import com.regisoc.modules.matches.application.CreateMatchCommand
import com.regisoc.modules.matches.application.CreateMatchUseCase
import com.regisoc.modules.matches.application.GetMatchUseCase
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
@RequestMapping("/api/matches")
class MatchController(
    private val createMatchUseCase: CreateMatchUseCase,
    private val getMatchUseCase: GetMatchUseCase
) {
    @PostMapping
    fun create(@Valid @RequestBody request: CreateMatchRequest): ResponseEntity<MatchResponse> {
        val command = CreateMatchCommand(
            matchDateId = request.matchDateId,
            homeClubId = request.homeClubId,
            awayClubId = request.awayClubId,
            scheduledTime = request.scheduledTime
        )
        val match = createMatchUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(MatchResponse.from(match))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<MatchResponse> {
        val match = getMatchUseCase.findById(id)
        return ResponseEntity.ok(MatchResponse.from(match))
    }

    @GetMapping
    fun getAll(
        @RequestParam(required = false) matchDateId: String?,
        @RequestParam(required = false) clubId: String?
    ): ResponseEntity<List<MatchResponse>> {
        val matches = when {
            matchDateId != null -> getMatchUseCase.findByMatchDate(matchDateId)
            clubId != null -> getMatchUseCase.findByClub(clubId)
            else -> emptyList()
        }
        return ResponseEntity.ok(matches.map { MatchResponse.from(it) })
    }
}
