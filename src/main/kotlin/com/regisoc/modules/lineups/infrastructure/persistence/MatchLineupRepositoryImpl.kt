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
    override fun saveAll(lineups: List<MatchLineup>): List<MatchLineup> = jpaRepository.saveAll(lineups)
    override fun findById(id: String): Optional<MatchLineup> = jpaRepository.findById(id)
    override fun findAllByMatchId(matchId: String): List<MatchLineup> = jpaRepository.findAllByMatchId(matchId)
    override fun findAllByMatchIdAndClubId(matchId: String, clubId: String): List<MatchLineup> =
        jpaRepository.findAllByMatchIdAndClubId(matchId, clubId)
    override fun deleteByMatchIdAndClubId(matchId: String, clubId: String) =
        jpaRepository.deleteByMatchIdAndClubId(matchId, clubId)
}
