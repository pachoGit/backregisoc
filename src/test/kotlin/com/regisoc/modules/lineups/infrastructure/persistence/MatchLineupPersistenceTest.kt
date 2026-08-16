package com.regisoc.modules.lineups.infrastructure.persistence

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.coaches.domain.Coach
import com.regisoc.modules.coaches.domain.CoachRepository
import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.events.domain.EventRepository
import com.regisoc.modules.lineups.domain.LineupCoach
import com.regisoc.modules.lineups.domain.LineupPhysicalTrainer
import com.regisoc.modules.lineups.domain.LineupPlayer
import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.modules.lineups.domain.MatchLineupRepository
import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.matches.domain.MatchRepository
import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.matchdates.domain.MatchDateRepository
import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainer
import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainerRepository
import com.regisoc.modules.players.domain.Player
import com.regisoc.modules.players.domain.PlayerPosition
import com.regisoc.modules.players.domain.PlayerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
class MatchLineupPersistenceTest {

    @Autowired
    private lateinit var clubRepository: ClubRepository

    @Autowired
    private lateinit var eventRepository: EventRepository

    @Autowired
    private lateinit var matchDateRepository: MatchDateRepository

    @Autowired
    private lateinit var matchRepository: MatchRepository

    @Autowired
    private lateinit var playerRepository: PlayerRepository

    @Autowired
    private lateinit var coachRepository: CoachRepository

    @Autowired
    private lateinit var physicalTrainerRepository: PhysicalTrainerRepository

    @Autowired
    private lateinit var lineupRepository: MatchLineupRepository

    private fun createMatch(): Match {
        val homeClub = clubRepository.save(Club(name = "Home FC", createdBy = "user1"))
        val awayClub = clubRepository.save(Club(name = "Away FC", createdBy = "user1"))
        val event = eventRepository.save(
            Event(
                name = "Test Event",
                location = "Test Location",
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusMonths(1)
            )
        )
        val matchDate = matchDateRepository.save(
            MatchDate(event = event, name = "Fecha 1", date = LocalDate.now())
        )
        return matchRepository.save(
            Match(
                matchDate = matchDate,
                homeClub = homeClub,
                awayClub = awayClub,
                scheduledTime = LocalDateTime.now()
            )
        )
    }

    @Test
    fun `persists and loads lineup with copied snapshots`() {
        val match = createMatch()
        val homeClub = match.homeClub

        val player = playerRepository.save(
            Player(
                club = homeClub,
                firstName = "John",
                lastName = "Doe",
                documentNumber = "12345678",
                age = 25,
                dateOfBirth = LocalDate.of(1999, 1, 1),
                position = PlayerPosition.FORWARD,
                photoUrl = "https://photo.url"
            )
        )
        val coach = coachRepository.save(
            Coach(
                club = homeClub,
                firstName = "Carlos",
                lastName = "Bianchi",
                documentNumber = "11222333",
                age = 55,
                dateOfBirth = LocalDate.of(1970, 4, 26)
            )
        )
        val physicalTrainer = physicalTrainerRepository.save(
            PhysicalTrainer(
                club = homeClub,
                firstName = "Fernando",
                lastName = "Signorini",
                documentNumber = "33444555",
                age = 60,
                dateOfBirth = LocalDate.of(1965, 3, 12)
            )
        )

        val lineup = MatchLineup(match = match, club = homeClub)
        lineup.setLineup(
            players = listOf(LineupPlayer.from(player)),
            coach = LineupCoach.from(coach),
            physicalTrainer = LineupPhysicalTrainer.from(physicalTrainer)
        )
        val saved = lineupRepository.save(lineup)

        player.update(
            firstName = "Changed",
            lastName = "Name",
            documentNumber = "99999999",
            age = 30,
            dateOfBirth = LocalDate.of(1994, 1, 1),
            position = null,
            photoUrl = null,
            documentFrontUrl = null,
            documentBackUrl = null
        )
        coach.update(
            firstName = "New",
            lastName = "Coach",
            documentNumber = "00000000",
            age = 60,
            dateOfBirth = LocalDate.of(1965, 1, 1),
            photoUrl = null
        )

        val loaded = lineupRepository.findByMatchIdAndClubId(match.id, homeClub.id)
            .orElseThrow { IllegalStateException("Lineup not found") }

        assertEquals(saved.id, loaded.id)
        assertEquals(1, loaded.players.size)
        assertEquals("John", loaded.players[0].firstName)
        assertEquals("Doe", loaded.players[0].lastName)
        assertEquals("12345678", loaded.players[0].documentNumber)
        assertEquals(PlayerPosition.FORWARD, loaded.players[0].position)
        assertEquals("Carlos", loaded.coach?.firstName)
        assertEquals("Bianchi", loaded.coach?.lastName)
        assertEquals("Fernando", loaded.physicalTrainer?.firstName)
        assertEquals("Signorini", loaded.physicalTrainer?.lastName)
    }

    @Test
    fun `persists and loads lineup without optional members`() {
        val match = createMatch()
        val homeClub = match.homeClub

        val coach = coachRepository.save(
            Coach(
                club = homeClub,
                firstName = "Carlos",
                lastName = "Bianchi",
                documentNumber = "11222333",
                age = 55,
                dateOfBirth = LocalDate.of(1970, 4, 26)
            )
        )

        val lineup = MatchLineup(match = match, club = homeClub)
        lineup.setLineup(
            players = emptyList(),
            coach = LineupCoach.from(coach),
            physicalTrainer = null
        )
        lineupRepository.save(lineup)

        val loaded = lineupRepository.findByMatchIdAndClubId(match.id, homeClub.id)
            .orElseThrow { IllegalStateException("Lineup not found") }

        assertEquals(0, loaded.players.size)
        assertEquals("Carlos", loaded.coach?.firstName)
        assertNull(loaded.physicalTrainer)
    }
}
