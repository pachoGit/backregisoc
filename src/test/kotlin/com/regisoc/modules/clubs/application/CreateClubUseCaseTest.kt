package com.regisoc.modules.clubs.application

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.clubs.domain.ClubRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CreateClubUseCaseTest {

    @Test
    fun `should create club successfully`() {
        val repository = mockk<ClubRepository>()
        val useCase = CreateClubUseCase(repository)

        val command = CreateClubCommand(
            name = "Test FC",
            foundedYear = 1900,
            crestUrl = null,
            description = "Test club",
            createdBy = "user1"
        )

        every { repository.save(any()) } answers { firstArg() }

        val result = useCase.execute(command)

        assertEquals("Test FC", result.name)
        assertEquals(1900, result.foundedYear)
        assertEquals("user1", result.createdBy)
        verify { repository.save(any()) }
    }
}
