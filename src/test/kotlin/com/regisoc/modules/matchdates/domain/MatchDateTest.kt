package com.regisoc.modules.matchdates.domain

import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.events.domain.EventStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class MatchDateTest {

    @Test
    fun `should create match date`() {
        val event = Event(
            name = "Test Event",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1)
        )
        val matchDate = MatchDate(
            event = event,
            name = "Fecha 1",
            date = LocalDate.now()
        )
        assertNotNull(matchDate.id)
        assertEquals(event.id, matchDate.event.id)
        assertEquals("Fecha 1", matchDate.name)
    }

    @Test
    fun `should update match date`() {
        val event = Event(
            name = "Test Event",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1)
        )
        val matchDate = MatchDate(event = event, name = "Fecha 1", date = LocalDate.now())
        matchDate.update("Fecha 2", LocalDate.now().plusDays(7))
        assertEquals("Fecha 2", matchDate.name)
    }
}
