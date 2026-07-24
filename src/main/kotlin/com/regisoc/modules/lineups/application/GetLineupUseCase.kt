package com.regisoc.modules.lineups.application

import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.modules.lineups.domain.MatchLineupRepository
import org.springframework.stereotype.Service

@Service
class GetLineupUseCase(private val repository: MatchLineupRepository) {
    fun getMatchLineups(matchId: String): List<MatchLineup> =
        repository.findAllByMatchId(matchId)

    fun getClubLineup(matchId: String, clubId: String): List<MatchLineup> =
        repository.findAllByMatchIdAndClubId(matchId, clubId)
}
