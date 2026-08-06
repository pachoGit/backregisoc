package com.regisoc.modules.lineups.infrastructure.persistence

import com.regisoc.modules.lineups.domain.MatchLineup
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MatchLineupJpaRepository : JpaRepository<MatchLineup, Long> {
    fun findAllByMatchId(matchId: Long): List<MatchLineup>
    fun findByMatchIdAndClubId(matchId: Long, clubId: Long): Optional<MatchLineup>
}
