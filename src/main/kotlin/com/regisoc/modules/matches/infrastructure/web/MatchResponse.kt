package com.regisoc.modules.matches.infrastructure.web

import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.matches.domain.MatchStatus
import java.time.LocalDateTime

data class MatchResponse(
    val id: String,
    val matchDateId: String,
    val homeClubId: String,
    val awayClubId: String,
    val scheduledTime: LocalDateTime,
    val status: MatchStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(match: Match) = MatchResponse(
            id = match.id,
            matchDateId = match.matchDate.id,
            homeClubId = match.homeClub.id,
            awayClubId = match.awayClub.id,
            scheduledTime = match.scheduledTime,
            status = match.status,
            createdAt = match.createdAt,
            updatedAt = match.updatedAt
        )
    }
}
