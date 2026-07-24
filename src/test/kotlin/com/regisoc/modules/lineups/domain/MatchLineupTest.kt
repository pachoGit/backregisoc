package com.regisoc.modules.lineups.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.matches.domain.MatchStatus
import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.events.domain.EventStatus
import com.regisoc.modules.players.domain.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class MatchLineupTest {

    @Test
    fun `should create lineup entry`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        val event = Event(
            name = "Test Event",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1)
        )
        val matchDate = MatchDate(event = event, name = "Fecha 1", date = LocalDate.now())
        val match = Match(
            matchDate = matchDate,
            homeClub = club,
            awayClub = Club(name = "Visitor FC", createdBy = "user1"),
            scheduledTime = LocalDateTime.now(),
            status = MatchStatus.SCHEDULED
        )
        val player = Player(
            club = club,
            firstName = "John",
            lastName = "Doe",
            documentNumber = "12345678",
            age = 25,
            dateOfBirth = LocalDate.of(1999, 1, 1)
        )

        val lineup = MatchLineup(match = match, club = club, player = player)
        assertNotNull(lineup.id)
        assertEquals(match.id, lineup.match.id)
        assertEquals(club.id, lineup.club.id)
        assertEquals(player.id, lineup.player.id)
    }
}
