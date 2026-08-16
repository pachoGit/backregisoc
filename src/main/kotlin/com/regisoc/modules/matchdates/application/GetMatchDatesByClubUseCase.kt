package com.regisoc.modules.matchdates.application

import com.regisoc.modules.events.domain.EventRegistrationRepository
import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.matchdates.domain.MatchDateRepository
import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.matches.domain.MatchRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import com.regisoc.modules.matchdates.infrastructure.web.GetByEventAndClubMatchDateResponse
import com.regisoc.modules.matchdates.infrastructure.web.GetByEventAndClubMatchResponse
import com.regisoc.modules.matchdates.infrastructure.web.GetByEventAndClubClubResponse
import kotlin.collections.mutableListOf

@Service
class GetMatchDatesByClubUseCase(
    private val repository: MatchDateRepository,
    private val registrationRepository: EventRegistrationRepository,
    private val matchRepository: MatchRepository
) {
    fun findByEventAndClub(eventId: Long, clubId: Long): List<GetByEventAndClubMatchDateResponse> {
        registrationRepository.findByEventIdAndClubId(eventId, clubId)
            .orElseThrow { EntityNotFoundException("Club ${clubId} is not registered for event ${eventId}") }

        val matchDates = repository.findAllByEventId(eventId)
        val matchesByMatchDateId = matchRepository
            .findByEventIdAndClubId(eventId, clubId)
            .groupBy { it.matchDate.id }

        return matchDates.map {
            val match = matchesByMatchDateId[it.id]?.firstOrNull()
            GetByEventAndClubMatchDateResponse.from(it, match)
        }
    }
}
