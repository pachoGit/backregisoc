package com.regisoc.modules.lineups.infrastructure.web

import com.regisoc.modules.lineups.application.GetLineupUseCase
import com.regisoc.modules.lineups.application.SetLineupCommand
import com.regisoc.modules.lineups.application.SetLineupUseCase
import com.regisoc.modules.lineups.application.CloseLineupUseCase
import com.regisoc.modules.lineups.application.CloseLineupCommand
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
import org.springframework.web.bind.annotation.PutMapping

@RestController
@RequestMapping("/api/lineups")
class LineupController(
    private val setLineupUseCase: SetLineupUseCase,
    private val getLineupUseCase: GetLineupUseCase,
    private val closeLineupUseCase: CloseLineupUseCase,
) {
    @PostMapping
    fun setLineup(@Valid @RequestBody request: SetLineupRequest): ResponseEntity<LineupResponse> {
        val command = SetLineupCommand(
            matchId = request.matchId,
            clubId = request.clubId,
            playerIds = request.playerIds,
            coachId = request.coachId,
            physicalTrainerId = request.physicalTrainerId
        )
        val lineup = setLineupUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(LineupResponse.from(lineup))
    }

    @GetMapping("/match/{matchId}")
    fun getMatchLineups(
        @PathVariable matchId: Long,
        @RequestParam(required = false) clubId: Long?
    ): ResponseEntity<List<LineupResponse>> {
        val lineups = if (clubId != null) {
            listOf(getLineupUseCase.getClubLineup(matchId, clubId))
        } else {
            getLineupUseCase.getMatchLineups(matchId)
        }
        return ResponseEntity.ok(lineups.map { LineupResponse.from(it) })
    }

    @GetMapping("/match/{matchId}/club/{clubId}")
    fun getOfClub(
        @PathVariable matchId: Long,
        @PathVariable clubId: Long
    ): ResponseEntity<LineupResponse> {
        val lineup = getLineupUseCase.getClubLineup(matchId, clubId)
        return ResponseEntity.ok(LineupResponse.from(lineup))
    }

    @PutMapping("/close/{lineupId}")
    fun close(@PathVariable lineupId: Long): ResponseEntity<Void> {
        val command = CloseLineupCommand(lineupId = lineupId)
        closeLineupUseCase.execute(command)
        return ResponseEntity.noContent().build()
    }
}
