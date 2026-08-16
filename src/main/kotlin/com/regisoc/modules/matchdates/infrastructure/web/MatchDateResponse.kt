package com.regisoc.modules.matchdates.infrastructure.web

import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.matchdates.domain.MatchDateStatus
import com.regisoc.modules.matches.infrastructure.web.MatchResponse
import java.time.LocalDate
import java.time.LocalDateTime

data class MatchDateResponse(
    val id: Long,
    val eventId: Long,
    val name: String,
    val date: LocalDate,
    val status: MatchDateStatus,
    val match: MatchResponse? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(matchDate: MatchDate) = MatchDateResponse(
            id = matchDate.id,
            eventId = matchDate.event.id,
            name = matchDate.name,
            date = matchDate.date,
            status = matchDate.status,
            createdAt = matchDate.createdAt,
            updatedAt = matchDate.updatedAt
        )

        fun from(matchDate: MatchDate, match: MatchResponse?) = MatchDateResponse(
            id = matchDate.id,
            eventId = matchDate.event.id,
            name = matchDate.name,
            date = matchDate.date,
            status = matchDate.status,
            match = match,
            createdAt = matchDate.createdAt,
            updatedAt = matchDate.updatedAt
        )
    }
}
