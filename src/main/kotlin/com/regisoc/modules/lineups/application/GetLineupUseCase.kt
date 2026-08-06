package com.regisoc.modules.lineups.application

import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.modules.lineups.domain.MatchLineupRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetLineupUseCase(private val repository: MatchLineupRepository) {
    fun getMatchLineups(matchId: Long): List<MatchLineup> =
        repository.findAllByMatchId(matchId)

    fun getClubLineup(matchId: Long, clubId: Long): MatchLineup =
        repository.findByMatchIdAndClubId(matchId, clubId)
            .orElseThrow { EntityNotFoundException("Lineup not found for match $matchId and club $clubId") }
}
