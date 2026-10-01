package com.regisoc.modules.lineups.infrastructure.persistence

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.modules.matches.domain.Match
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Optional

class MatchLineupRepositoryImplTest {

    private val jpaRepository = mockk<MatchLineupJpaRepository>()
    private lateinit var repository: MatchLineupRepositoryImpl

    @BeforeEach
    fun setUp() {
        repository = MatchLineupRepositoryImpl(jpaRepository)
    }

    @Test
    fun `should delegate save to jpa repository`() {
        val lineup = createLineup()

        every { jpaRepository.save(lineup) } returns lineup

        val result = repository.save(lineup)

        assertEquals(lineup, result)
        verify { jpaRepository.save(lineup) }
    }

    @Test
    fun `should delegate findAllByMatchId to jpa repository`() {
        val lineup = createLineup()

        every { jpaRepository.findAllByMatchId(1L) } returns listOf(lineup)

        val result = repository.findAllByMatchId(1L)

        assertEquals(listOf(lineup), result)
        verify { jpaRepository.findAllByMatchId(1L) }
    }

    @Test
    fun `should delegate findByMatchIdAndClubId to jpa repository`() {
        val lineup = createLineup()

        every { jpaRepository.findByMatchIdAndClubId(1L, 2L) } returns Optional.of(lineup)

        val result = repository.findByMatchIdAndClubId(1L, 2L)

        assertTrue(result.isPresent)
        assertEquals(lineup, result.get())
        verify { jpaRepository.findByMatchIdAndClubId(1L, 2L) }
    }

    @Test
    fun `should delegate findById to jpa repository`() {
        val lineup = createLineup()

        every { jpaRepository.findById(5L) } returns Optional.of(lineup)

        val result = repository.findById(5L)

        assertTrue(result.isPresent)
        assertEquals(lineup, result.get())
        verify { jpaRepository.findById(5L) }
    }

    private fun createLineup(): MatchLineup {
        val match = mockk<Match>(relaxed = true)
        val club = mockk<Club>(relaxed = true)
        return MatchLineup(match = match, club = club)
    }
}
