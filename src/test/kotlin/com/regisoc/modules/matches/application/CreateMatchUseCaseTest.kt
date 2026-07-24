package com.regisoc.modules.matches.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.matchdates.domain.MatchDateRepository
import com.regisoc.modules.matches.domain.MatchRepository
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class CreateMatchUseCaseTest {

    @Test
    fun `should throw when home and away clubs are the same`() {
        val repository = mockk<MatchRepository>()
        val matchDateRepository = mockk<MatchDateRepository>()
        val clubRepository = mockk<ClubRepository>()
        val useCase = CreateMatchUseCase(repository, matchDateRepository, clubRepository)

        val command = CreateMatchCommand(
            matchDateId = "md1",
            homeClubId = "sameClub",
            awayClubId = "sameClub",
            scheduledTime = LocalDateTime.now()
        )

        val exception = assertThrows<IllegalArgumentException> { useCase.execute(command) }
        assertTrue(exception.message?.contains("different") == true)
    }
}
