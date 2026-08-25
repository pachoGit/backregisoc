package com.regisoc.modules.auth.application

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.users.domain.User
import com.regisoc.modules.users.domain.UserRepository
import com.regisoc.modules.users.domain.UserRole
import com.regisoc.shared.infrastructure.security.CurrentUserHelper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional

class GetMeUseCaseTest {

    private val currentUserHelper = mockk<CurrentUserHelper>()
    private val userRepository = mockk<UserRepository>()
    private lateinit var getMeUseCase: GetMeUseCase

    @BeforeEach
    fun setUp() {
        getMeUseCase = GetMeUseCase(currentUserHelper, userRepository)
    }

    @Test
    fun `should return admin user without club`() {
        val user = User(
            name = "Admin", surname = "User", documentNumber = "11111111",
            username = "admin", password = "hash", role = UserRole.ADMIN
        )

        every { currentUserHelper.getCurrentUserId() } returns 1L
        every { userRepository.findById(1L) } returns Optional.of(user)

        val result = getMeUseCase.execute()

        assertEquals(user.id, result.id)
        assertEquals("Admin", result.name)
        assertEquals("admin", result.username)
        assertEquals("ADMIN", result.role)
        assertNull(result.club)
    }

    @Test
    fun `should return club manager user with club`() {
        val club = Club(name = "Club Test", foundedYear = 2000, createdBy = "admin")
        val user = User(
            name = "Carlos", surname = "Garcia", documentNumber = "88888888",
            username = "carlos", password = "hash", role = UserRole.CLUB_MANAGER, club = club
        )

        every { currentUserHelper.getCurrentUserId() } returns 2L
        every { userRepository.findById(2L) } returns Optional.of(user)

        val result = getMeUseCase.execute()

        assertEquals("Carlos", result.name)
        assertEquals("CLUB_MANAGER", result.role)
        assertNotNull(result.club)
        assertEquals(club.id, result.club!!.id)
        assertEquals("Club Test", result.club!!.name)
    }

    @Test
    fun `should return null club when user has no club`() {
        val user = User(
            name = "Ghost", surname = "User", documentNumber = "99999999",
            username = "ghost", password = "hash", role = UserRole.CLUB_MANAGER
        )

        every { currentUserHelper.getCurrentUserId() } returns 3L
        every { userRepository.findById(3L) } returns Optional.of(user)

        val result = getMeUseCase.execute()

        assertNull(result.club)
    }

    @Test
    fun `should throw when user not found`() {
        every { currentUserHelper.getCurrentUserId() } returns 999L
        every { userRepository.findById(999L) } returns Optional.empty()

        assertThrows<IllegalStateException> {
            getMeUseCase.execute()
        }
    }
}
