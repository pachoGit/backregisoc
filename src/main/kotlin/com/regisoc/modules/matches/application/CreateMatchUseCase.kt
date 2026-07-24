package com.regisoc.modules.matches.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.matchdates.domain.MatchDateRepository
import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.matches.domain.MatchRepository
import com.regisoc.modules.matches.domain.MatchStatus
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

data class CreateMatchCommand(
    val matchDateId: Long,
    val homeClubId: Long,
    val awayClubId: Long,
    val scheduledTime: LocalDateTime
)

@Service
class CreateMatchUseCase(
    private val repository: MatchRepository,
    private val matchDateRepository: MatchDateRepository,
    private val clubRepository: ClubRepository
) {
    fun execute(command: CreateMatchCommand): Match {
        if (command.homeClubId == command.awayClubId) {
            throw IllegalArgumentException("Home and away clubs must be different")
        }

        val matchDate = matchDateRepository.findById(command.matchDateId)
            .orElseThrow { EntityNotFoundException("MatchDate not found: ${command.matchDateId}") }
        val homeClub = clubRepository.findById(command.homeClubId)
            .orElseThrow { EntityNotFoundException("Club not found: ${command.homeClubId}") }
        val awayClub = clubRepository.findById(command.awayClubId)
            .orElseThrow { EntityNotFoundException("Club not found: ${command.awayClubId}") }

        val match = Match(
            matchDate = matchDate,
            homeClub = homeClub,
            awayClub = awayClub,
            scheduledTime = command.scheduledTime,
            status = MatchStatus.SCHEDULED
        )
        return repository.save(match)
    }
}
