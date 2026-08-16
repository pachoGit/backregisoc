package com.regisoc.modules.matches.infrastructure.persistence

import com.regisoc.modules.matches.domain.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MatchJpaRepository : JpaRepository<Match, Long> {
    fun findAllByMatchDateId(matchDateId: Long): List<Match>

    @Query("SELECT m FROM Match m JOIN m.matchDate md WHERE md.event.id = :eventId")
    fun findAllByEventId(@Param("eventId") eventId: Long): List<Match>

    @Query("""
        SELECT m FROM Match m
        JOIN m.matchDate md
        JOIN FETCH m.homeClub homeClub
        JOIN FETCH m.awayClub awayClub
        WHERE md.event.id = :eventId
        AND m.homeClub.id = :clubId
        OR m.awayClub.id = :clubId
           """)
    fun findByEventIdAndClubId(@Param("eventId") eventId: Long, @Param("clubId") clubId: Long): List<Match>

    @Query("SELECT m FROM Match m WHERE m.homeClub.id = :clubId OR m.awayClub.id = :clubId")
    fun findByClubId(@Param("clubId") clubId: Long): List<Match>
}
