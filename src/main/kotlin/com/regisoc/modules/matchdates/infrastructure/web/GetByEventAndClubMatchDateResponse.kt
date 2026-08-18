package com.regisoc.modules.matchdates.infrastructure.web

import com.regisoc.modules.matchdates.domain.MatchDateStatus
import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.matches.domain.MatchStatus
import com.regisoc.modules.matches.domain.Match
import java.time.LocalDate
import java.time.LocalDateTime

data class GetByEventAndClubMatchDateResponse(
    val id: Long,
    val name: String,
    val date: LocalDate,
    val status: MatchDateStatus,
    val match: GetByEventAndClubMatchResponse? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime

) {
    companion object {
        fun from(matchDate: MatchDate, match: Match?) = GetByEventAndClubMatchDateResponse(
            id = matchDate.id,
            name = matchDate.name,
            date = matchDate.date,
            status = matchDate.status,
            match = GetByEventAndClubMatchResponse.fromMatch(match),
            createdAt = matchDate.createdAt,
            updatedAt = matchDate.updatedAt,
        ) 
    }
}

data class GetByEventAndClubMatchResponse(
    val id: Long,
    val homeClub: GetByEventAndClubClubResponse,
    val awayClub: GetByEventAndClubClubResponse,
    val scheduledTime: LocalDateTime,
    val status: MatchStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun fromMatch(match: Match?): GetByEventAndClubMatchResponse? {
            if (match == null)
                return null
            return GetByEventAndClubMatchResponse(
                id = match.id,
                homeClub = GetByEventAndClubClubResponse.homeClub(match),
                awayClub = GetByEventAndClubClubResponse.awayClub(match),
                scheduledTime = match.scheduledTime,
                status = match.status,
                createdAt = match.createdAt,
                updatedAt = match.updatedAt,
            )
        }
    }
}

data class GetByEventAndClubClubResponse(
    val id: Long,
    val name: String,
    val crestUrl: String?,
) {
    companion object {
        fun homeClub(match: Match) = GetByEventAndClubClubResponse(
            id = match.homeClub.id,
            name = match.homeClub.name,
            crestUrl = match.homeClub.crestUrl
        )

        fun awayClub(match: Match) = GetByEventAndClubClubResponse(
            id = match.awayClub.id,
            name = match.awayClub.name,
            crestUrl = match.awayClub.crestUrl
        )
    }
}
