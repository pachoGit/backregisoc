package com.regisoc.modules.lineups.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.coaches.domain.CoachRepository
import com.regisoc.modules.lineups.domain.LineupCoach
import com.regisoc.modules.lineups.domain.LineupPhysicalTrainer
import com.regisoc.modules.lineups.domain.LineupPlayer
import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.modules.lineups.domain.MatchLineupRepository
import com.regisoc.modules.matches.domain.MatchRepository
import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainerRepository
import com.regisoc.modules.players.domain.PlayerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

data class SetLineupCommand(
    val matchId: Long,
    val clubId: Long,
    val playerIds: List<Long>,
    val coachId: Long?,
    val physicalTrainerId: Long?
)

@Service
class SetLineupUseCase(
    private val repository: MatchLineupRepository,
    private val matchRepository: MatchRepository,
    private val clubRepository: ClubRepository,
    private val playerRepository: PlayerRepository,
    private val coachRepository: CoachRepository,
    private val physicalTrainerRepository: PhysicalTrainerRepository
) {
    fun execute(command: SetLineupCommand): MatchLineup {
        require(command.playerIds.size <= MAX_PLAYERS) {
            "Lineup cannot have more than $MAX_PLAYERS players"
        }
        require(command.playerIds.distinct().size == command.playerIds.size) {
            "Duplicate player ids are not allowed"
        }

        val match = matchRepository.findById(command.matchId)
            .orElseThrow { EntityNotFoundException("Match not found: ${command.matchId}") }
        val club = clubRepository.findById(command.clubId)
            .orElseThrow { EntityNotFoundException("Club not found: ${command.clubId}") }

        val players = command.playerIds.map { playerId ->
            val player = playerRepository.findById(playerId)
                .orElseThrow { EntityNotFoundException("Player not found: $playerId") }
            LineupPlayer.from(player)
        }

        val coach = command.coachId?.let { coachId ->
            coachRepository.findById(coachId)
                .orElseThrow { EntityNotFoundException("Coach not found: $coachId") }
        }

        // val coach = coachRepository.findById(command.coachId)
        //     .orElseThrow { EntityNotFoundException("Coach not found: ${command.coachId}") }

        val physicalTrainer = command.physicalTrainerId?.let { physicalTrainerId ->
            physicalTrainerRepository.findById(physicalTrainerId)
                .orElseThrow { EntityNotFoundException("PhysicalTrainer not found: $physicalTrainerId") }
        }

        val lineup = repository.findByMatchIdAndClubId(match.id, club.id)
            .orElseGet { MatchLineup(match = match, club = club) }

        lineup.setLineup(
            players = players,
            coach = coach?.let { LineupCoach.from(it) },
            physicalTrainer = physicalTrainer?.let { LineupPhysicalTrainer.from(it) }
        )
        return repository.save(lineup)
    }

    private companion object {
        const val MAX_PLAYERS = 22
    }
}
