package com.regisoc.modules.players.domain

import com.regisoc.modules.clubs.domain.Club
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PlayerTest {

    @Test
    fun `should create player with basic info`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        val player = Player(club = club, name = "John Doe")
        assertNotNull(player.id)
        assertEquals(club.id, player.club.id)
        assertEquals("John Doe", player.name)
        assertNull(player.position)
    }

    @Test
    fun `should create player with full info`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        val player = Player(
            club = club,
            name = "Jane Doe",
            dateOfBirth = LocalDate.of(1995, 5, 15),
            position = PlayerPosition.FORWARD,
            photoUrl = "https://photo.url",
            documentFrontUrl = "https://doc-front.url",
            documentBackUrl = "https://doc-back.url"
        )
        assertEquals(PlayerPosition.FORWARD, player.position)
        assertEquals("https://photo.url", player.photoUrl)
    }

    @Test
    fun `should update player details`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        val player = Player(club = club, name = "Old Name")
        player.update("New Name", LocalDate.of(2000, 1, 1), PlayerPosition.DEFENDER, null, null, null)
        assertEquals("New Name", player.name)
        assertEquals(PlayerPosition.DEFENDER, player.position)
    }

    @Test
    fun `should update player photos`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        val player = Player(club = club, name = "Player")
        player.updatePhotos("https://new-photo.url", "https://new-doc-front.url", "https://new-doc-back.url")
        assertEquals("https://new-photo.url", player.photoUrl)
        assertEquals("https://new-doc-front.url", player.documentFrontUrl)
    }
}
