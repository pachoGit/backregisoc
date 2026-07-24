package com.regisoc.modules.events.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class EventTest {

    @Test
    fun `should create event with upcoming status`() {
        val event = Event(
            name = "Torneo Apertura",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(3)
        )
        assertEquals(EventStatus.UPCOMING, event.status)
    }

    @Test
    fun `should change event status`() {
        val event = Event(
            name = "Torneo Clausura",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(3)
        )
        event.changeStatus(EventStatus.ONGOING)
        assertEquals(EventStatus.ONGOING, event.status)
    }

    @Test
    fun `should update event details`() {
        val event = Event(
            name = "Old Event",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1)
        )
        event.update("New Event", "Updated description", LocalDate.now(), LocalDate.now().plusMonths(2))
        assertEquals("New Event", event.name)
        assertEquals("Updated description", event.description)
    }
}
