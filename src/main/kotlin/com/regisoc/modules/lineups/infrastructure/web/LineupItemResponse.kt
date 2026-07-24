package com.regisoc.modules.lineups.infrastructure.web

import com.regisoc.modules.lineups.domain.MatchLineup
import java.time.LocalDateTime

data class LineupItemResponse(
    val id: Long,
    val matchId: Long,
    val clubId: Long,
    val playerId: Long,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(lineup: MatchLineup) = LineupItemResponse(
            id = lineup.id,
            matchId = lineup.match.id,
            clubId = lineup.club.id,
            playerId = lineup.player.id,
            createdAt = lineup.createdAt
        )
    }
}
