package com.regisoc.modules.players.domain

import com.regisoc.modules.clubs.domain.Club
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PlayerTest {

    @Test
    fun `should create player with basic info`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        val player = Player(
            club = club,
            firstName = "John",
            lastName = "Doe",
            documentNumber = "12345678",
            age = 25,
            dateOfBirth = LocalDate.of(1999, 1, 1)
        )
        assertNotNull(player.id)
        assertEquals(club.id, player.club.id)
        assertEquals("John", player.firstName)
        assertEquals("Doe", player.lastName)
        assertEquals("12345678", player.documentNumber)
        assertEquals(25, player.age)
        assertNull(player.position)
    }

    @Test
    fun `should create player with full info`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        val player = Player(
            club = club,
            firstName = "Jane",
            lastName = "Doe",
            documentNumber = "87654321",
            age = 29,
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
        val player = Player(
            club = club,
            firstName = "Old First",
            lastName = "Old Last",
            documentNumber = "11111111",
            age = 20,
            dateOfBirth = LocalDate.of(2000, 1, 1)
        )
        player.update(
            firstName = "New First",
            lastName = "New Last",
            documentNumber = "22222222",
            age = 25,
            dateOfBirth = LocalDate.of(1999, 5, 10),
            position = PlayerPosition.DEFENDER,
            photoUrl = null,
            documentFrontUrl = null,
            documentBackUrl = null
        )
        assertEquals("New First", player.firstName)
        assertEquals("New Last", player.lastName)
        assertEquals("22222222", player.documentNumber)
        assertEquals(25, player.age)
        assertEquals(PlayerPosition.DEFENDER, player.position)
    }

    @Test
    fun `should update player photos`() {
        val club = Club(name = "Test FC", createdBy = "user1")
        val player = Player(
            club = club,
            firstName = "Photo",
            lastName = "Player",
            documentNumber = "33333333",
            age = 22,
            dateOfBirth = LocalDate.of(2002, 3, 15)
        )
        player.updatePhotos("https://new-photo.url", "https://new-doc-front.url", "https://new-doc-back.url")
        assertEquals("https://new-photo.url", player.photoUrl)
        assertEquals("https://new-doc-front.url", player.documentFrontUrl)
    }
}
