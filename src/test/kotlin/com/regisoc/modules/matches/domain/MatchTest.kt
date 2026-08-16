package com.regisoc.modules.matches.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.events.domain.EventStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class MatchTest {

    private fun createMatch(homeId: String = "club1", awayId: String = "club2"): Match {
        val homeClub = Club(name = "Home FC", createdBy = "user1")
        // We need a way to set the ID for testing
        val awayClub = Club(name = "Away FC", createdBy = "user1")
        val event = Event(
            name = "Test Event",
            location = "Test Location",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1)
        )
        val matchDate = MatchDate(event = event, name = "Fecha 1", date = LocalDate.now())
        return Match(
            matchDate = matchDate,
            homeClub = homeClub,
            awayClub = awayClub,
            scheduledTime = LocalDateTime.now()
        )
    }

    @Test
    fun `should create match with scheduled status`() {
        val match = createMatch()
        assertEquals(MatchStatus.UPCOMING, match.status)
        assertEquals("Home FC", match.homeClub.name)
        assertEquals("Away FC", match.awayClub.name)
    }

    @Test
    fun `should change match status`() {
        val match = createMatch()
        match.changeStatus(MatchStatus.ONGOING)
        assertEquals(MatchStatus.ONGOING, match.status)
    }

    @Test
    fun `should reschedule match`() {
        val originalTime = LocalDateTime.now()
        val match = createMatch()
        // Override scheduledTime
        val field = Match::class.java.getDeclaredField("scheduledTime")
        field.isAccessible = true
        field.set(match, originalTime)

        val newTime = originalTime.plusHours(2)
        match.reschedule(newTime)
        assertEquals(newTime, match.scheduledTime)
    }
}
