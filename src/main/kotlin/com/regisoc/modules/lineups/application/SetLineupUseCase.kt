package com.regisoc.modules.lineups.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.modules.lineups.domain.MatchLineupRepository
import com.regisoc.modules.matches.domain.MatchRepository
import com.regisoc.modules.players.domain.PlayerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

data class SetLineupCommand(
    val matchId: String,
    val clubId: String,
    val playerIds: List<String>
)

@Service
class SetLineupUseCase(
    private val repository: MatchLineupRepository,
    private val matchRepository: MatchRepository,
    private val clubRepository: ClubRepository,
    private val playerRepository: PlayerRepository
) {
    fun execute(command: SetLineupCommand): List<MatchLineup> {
        val match = matchRepository.findById(command.matchId)
            .orElseThrow { EntityNotFoundException("Match not found: ${command.matchId}") }
        val club = clubRepository.findById(command.clubId)
            .orElseThrow { EntityNotFoundException("Club not found: ${command.clubId}") }

        repository.deleteByMatchIdAndClubId(command.matchId, command.clubId)
        val lineups = command.playerIds.map { playerId ->
            val player = playerRepository.findById(playerId)
                .orElseThrow { EntityNotFoundException("Player not found: $playerId") }
            MatchLineup(match = match, club = club, player = player)
        }
        return repository.saveAll(lineups)
    }
}
