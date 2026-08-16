package com.regisoc.modules.matchdates.infrastructure.persistence

import com.regisoc.modules.matchdates.domain.MatchDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MatchDateJpaRepository : JpaRepository<MatchDate, Long> {
    fun findAllByEventId(eventId: Long): List<MatchDate>

    @Query("""
        SELECT DISTINCT md
        FROM MatchDate md
        JOIN md.matches m
        WHERE md.event.id = :eventId
          AND (
              m.homeClub.id = :clubId
              OR m.awayClub.id = :clubId
          )
           """)
    fun findByClubIdAndEventId(@Param("eventId") eventId: Long, clubId: Long): List<MatchDate>
}
