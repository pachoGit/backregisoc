package com.regisoc.modules.matchdates.infrastructure.web

import com.regisoc.modules.matchdates.domain.MatchDate
import java.time.LocalDate
import java.time.LocalDateTime

data class MatchDateResponse(
    val id: String,
    val eventId: String,
    val name: String,
    val date: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(matchDate: MatchDate) = MatchDateResponse(
            id = matchDate.id,
            eventId = matchDate.event.id,
            name = matchDate.name,
            date = matchDate.date,
            createdAt = matchDate.createdAt,
            updatedAt = matchDate.updatedAt
        )
    }
}
