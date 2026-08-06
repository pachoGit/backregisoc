package com.regisoc.modules.lineups.infrastructure.web

import com.regisoc.modules.lineups.domain.MatchLineup
import java.time.LocalDateTime

data class LineupResponse(
    val id: Long,
    val matchId: Long,
    val clubId: Long,
    val players: List<LineupPlayerResponse>,
    val coach: LineupCoachResponse?,
    val physicalTrainer: LineupPhysicalTrainerResponse?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(lineup: MatchLineup) = LineupResponse(
            id = lineup.id,
            matchId = lineup.match.id,
            clubId = lineup.club.id,
            players = lineup.players.map { LineupPlayerResponse.from(it) },
            coach = lineup.coach?.let { LineupCoachResponse.from(it) },
            physicalTrainer = lineup.physicalTrainer?.let { LineupPhysicalTrainerResponse.from(it) },
            createdAt = lineup.createdAt,
            updatedAt = lineup.updatedAt
        )
    }
}
