package com.regisoc.modules.matches.infrastructure.persistence

import com.regisoc.modules.matches.domain.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MatchJpaRepository : JpaRepository<Match, String> {
    fun findAllByMatchDateId(matchDateId: String): List<Match>

    @Query("SELECT m FROM Match m WHERE m.homeClub.id = :clubId OR m.awayClub.id = :clubId")
    fun findByClubId(@Param("clubId") clubId: String): List<Match>
}
