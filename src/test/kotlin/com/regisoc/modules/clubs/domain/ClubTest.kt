package com.regisoc.modules.clubs.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ClubTest {

    @Test
    fun `should create club with default values`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        assertNotNull(club.id)
        assertEquals("Test FC", club.name)
        assertTrue(club.isActive)
    }

    @Test
    fun `should update club details`() {
        val club = Club(name = "Old Name", createdBy = "user1")
        club.update("New Name", 1990, "https://crest.url", "Updated description")
        assertEquals("New Name", club.name)
        assertEquals(1990, club.foundedYear)
        assertEquals("https://crest.url", club.crestUrl)
    }

    @Test
    fun `should deactivate club`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        club.deactivate()
        assertFalse(club.isActive)
    }
}
