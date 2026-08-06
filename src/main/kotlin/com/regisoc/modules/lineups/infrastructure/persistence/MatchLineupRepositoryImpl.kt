package com.regisoc.modules.lineups.infrastructure.persistence

import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.modules.lineups.domain.MatchLineupRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class MatchLineupRepositoryImpl(
    private val jpaRepository: MatchLineupJpaRepository
) : MatchLineupRepository {
    override fun save(lineup: MatchLineup): MatchLineup = jpaRepository.save(lineup)
    override fun findAllByMatchId(matchId: Long): List<MatchLineup> = jpaRepository.findAllByMatchId(matchId)
    override fun findByMatchIdAndClubId(matchId: Long, clubId: Long): Optional<MatchLineup> =
        jpaRepository.findByMatchIdAndClubId(matchId, clubId)
}
