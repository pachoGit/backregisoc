package com.regisoc.modules.lineups.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.coaches.domain.Coach
import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.matches.domain.MatchStatus
import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainer
import com.regisoc.modules.players.domain.Player
import com.regisoc.modules.players.domain.PlayerPosition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class MatchLineupTest {

    private fun createMatch(): Match {
        val event = Event(
            name = "Test Event",
            location = "Test Location",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1)
        )
        val matchDate = MatchDate(event = event, name = "Fecha 1", date = LocalDate.now())
        return Match(
            matchDate = matchDate,
            homeClub = Club(name = "Test FC", createdBy = "user1"),
            awayClub = Club(name = "Visitor FC", createdBy = "user1"),
            scheduledTime = LocalDateTime.now(),
            status = MatchStatus.UPCOMING
        )
    }

    private fun createClub() = Club(name = "Test FC", createdBy = "user1")

    private fun createPlayer(club: Club) = Player(
        club = club,
        firstName = "John",
        lastName = "Doe",
        documentNumber = "12345678",
        age = 25,
        dateOfBirth = LocalDate.of(1999, 1, 1),
        position = PlayerPosition.FORWARD,
        photoUrl = "https://photo.url"
    )

    private fun createCoach(club: Club) = Coach(
        club = club,
        firstName = "Carlos",
        lastName = "Bianchi",
        documentNumber = "11222333",
        age = 55,
        dateOfBirth = LocalDate.of(1970, 4, 26)
    )

    private fun createPhysicalTrainer(club: Club) = PhysicalTrainer(
        club = club,
        firstName = "Fernando",
        lastName = "Signorini",
        documentNumber = "33444555",
        age = 60,
        dateOfBirth = LocalDate.of(1965, 3, 12)
    )

    @Test
    fun `should set lineup with player, coach and physical trainer snapshots`() {
        val club = createClub()
        val match = createMatch()

        val lineup = MatchLineup(match = match, club = club)
        lineup.setLineup(
            players = listOf(LineupPlayer.from(createPlayer(club))),
            coach = LineupCoach.from(createCoach(club)),
            physicalTrainer = LineupPhysicalTrainer.from(createPhysicalTrainer(club))
        )

        assertNotNull(lineup.id)
        assertEquals(match.id, lineup.match.id)
        assertEquals(club.id, lineup.club.id)
        assertEquals(1, lineup.players.size)
        assertEquals("John", lineup.players[0].firstName)
        assertEquals("Doe", lineup.players[0].lastName)
        assertEquals(PlayerPosition.FORWARD, lineup.players[0].position)
        assertEquals("Carlos", lineup.coach?.firstName)
        assertEquals("Bianchi", lineup.coach?.lastName)
        assertEquals("Fernando", lineup.physicalTrainer?.firstName)
        assertEquals("Signorini", lineup.physicalTrainer?.lastName)
    }

    @Test
    fun `lineup keeps the registered data after source entities change`() {
        val club = createClub()
        val match = createMatch()
        val player = createPlayer(club)
        val coach = createCoach(club)

        val lineup = MatchLineup(match = match, club = club)
        lineup.setLineup(
            players = listOf(LineupPlayer.from(player)),
            coach = LineupCoach.from(coach),
            physicalTrainer = null
        )

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

        assertEquals("John", lineup.players[0].firstName)
        assertEquals("Doe", lineup.players[0].lastName)
        assertEquals("Carlos", lineup.coach?.firstName)
        assertEquals("Bianchi", lineup.coach?.lastName)
    }

    @Test
    fun `should replace the previous lineup data`() {
        val club = createClub()
        val match = createMatch()
        val newCoach = Coach(
            club = club,
            firstName = "Marcelo",
            lastName = "Gallardo",
            documentNumber = "22333444",
            age = 49,
            dateOfBirth = LocalDate.of(1976, 1, 18)
        )

        val lineup = MatchLineup(match = match, club = club)
        lineup.setLineup(
            players = listOf(LineupPlayer.from(createPlayer(club))),
            coach = LineupCoach.from(createCoach(club)),
            physicalTrainer = LineupPhysicalTrainer.from(createPhysicalTrainer(club))
        )
        lineup.setLineup(
            players = emptyList(),
            coach = LineupCoach.from(newCoach),
            physicalTrainer = null
        )

        assertEquals(0, lineup.players.size)
        assertEquals("Marcelo", lineup.coach?.firstName)
        assertEquals("Gallardo", lineup.coach?.lastName)
        assertNull(lineup.physicalTrainer)
    }
}
