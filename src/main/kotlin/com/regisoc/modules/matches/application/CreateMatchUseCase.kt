package com.regisoc.modules.matches.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.matchdates.domain.MatchDateRepository
import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.matches.domain.MatchRepository
import com.regisoc.modules.matches.domain.MatchStatus
import com.regisoc.modules.events.domain.EventRegistrationRepository
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
    private val clubRepository: ClubRepository,
    private val registrationRepository: EventRegistrationRepository
) {
    fun execute(command: CreateMatchCommand): Match {
        if (command.homeClubId == command.awayClubId) {
            throw IllegalArgumentException("El club local y visitante deben ser distintos")
        }

        val matchDate = matchDateRepository.findById(command.matchDateId)
            .orElseThrow { EntityNotFoundException("La fecha no existe: ${command.matchDateId}") }

        val homeClub = clubRepository.findById(command.homeClubId)
            .orElseThrow { EntityNotFoundException("El club no existe: ${command.homeClubId}") }

        if (registrationRepository
                  .findByEventIdAndClubId(matchDate.event.id, homeClub.id)
                  .isEmpty) {
            throw IllegalStateException("El club ${homeClub.name}  no esta registrado al evento")
        }

        val awayClub = clubRepository.findById(command.awayClubId)
            .orElseThrow { EntityNotFoundException("El club no existe: ${command.awayClubId}") }

        if (registrationRepository
                .findByEventIdAndClubId(matchDate.event.id, awayClub.id)
                .isEmpty) {
            throw IllegalStateException("El Club ${awayClub.name} no esta registrado al evento")
        }

        if (matchDate.date.atTime(0, 0) > command.scheduledTime) {
            throw IllegalStateException("La fecha del partido tiene que ser mayor a la fecha del encuentro")
        }

        val match = Match(
            matchDate = matchDate,
            homeClub = homeClub,
            awayClub = awayClub,
            scheduledTime = command.scheduledTime,
            status = MatchStatus.UPCOMING
        )
        return repository.save(match)
    }
}
