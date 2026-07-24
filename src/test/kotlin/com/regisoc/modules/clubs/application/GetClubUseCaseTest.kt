package com.regisoc.modules.clubs.application

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.clubs.domain.ClubRepository
import io.mockk.every
import io.mockk.mockk
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional

class GetClubUseCaseTest {

    @Test
    fun `should find club by id`() {
        val repository = mockk<ClubRepository>()
        val useCase = GetClubUseCase(repository)
        val club = Club(name = "Test FC", createdBy = "user1")

        every { repository.findById(club.id) } returns Optional.of(club)

        val result = useCase.findById(club.id)
        assertEquals(club.id, result.id)
    }

    @Test
    fun `should throw when club not found`() {
        val repository = mockk<ClubRepository>()
        val useCase = GetClubUseCase(repository)

        every { repository.findById("nonexistent") } returns Optional.empty()

        assertThrows<EntityNotFoundException> { useCase.findById("nonexistent") }
    }
}
