package com.regisoc.modules.matchdates.infrastructure.persistence

import com.regisoc.modules.matchdates.domain.MatchDate
import org.springframework.data.jpa.repository.JpaRepository

interface MatchDateJpaRepository : JpaRepository<MatchDate, Long> {
    fun findAllByEventId(eventId: Long): List<MatchDate>
}
