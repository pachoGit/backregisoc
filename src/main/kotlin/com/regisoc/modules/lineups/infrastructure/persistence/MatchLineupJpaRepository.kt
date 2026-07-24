package com.regisoc.modules.lineups.infrastructure.persistence

import com.regisoc.modules.lineups.domain.MatchLineup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface MatchLineupJpaRepository : JpaRepository<MatchLineup, String> {
    fun findAllByMatchId(matchId: String): List<MatchLineup>
    fun findAllByMatchIdAndClubId(matchId: String, clubId: String): List<MatchLineup>

    @Modifying
    @Transactional
    @Query("DELETE FROM MatchLineup ml WHERE ml.match.id = :matchId AND ml.club.id = :clubId")
    fun deleteByMatchIdAndClubId(@Param("matchId") matchId: String, @Param("clubId") clubId: String)
}
