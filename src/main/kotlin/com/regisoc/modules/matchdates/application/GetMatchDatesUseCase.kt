package com.regisoc.modules.matchdates.application

import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.matchdates.domain.MatchDateRepository
import org.springframework.stereotype.Service

@Service
class GetMatchDatesUseCase(private val repository: MatchDateRepository) {
    fun findByEvent(eventId: Long): List<MatchDate> = repository.findAllByEventId(eventId)
}
