package com.regisoc.modules.events.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class EventTest {

    @Test
    fun `should create event with upcoming status`() {
        val event = Event(
            name = "Torneo Apertura",
            location = "Estadio Único de La Plata",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(3)
        )
        assertEquals(EventStatus.UPCOMING, event.status)
        assertEquals("Estadio Único de La Plata", event.location)
    }

    @Test
    fun `should change event status`() {
        val event = Event(
            name = "Torneo Clausura",
            location = "Estadio Libertadores de América",
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
            location = "Old Location",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1)
        )
        event.update(
            name = "New Event",
            location = "New Location",
            description = "Updated description",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(2)
        )
        assertEquals("New Event", event.name)
        assertEquals("New Location", event.location)
        assertEquals("Updated description", event.description)
    }
}
