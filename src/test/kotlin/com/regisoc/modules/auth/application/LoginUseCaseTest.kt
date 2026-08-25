package com.regisoc.modules.auth.application

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.users.domain.User
import com.regisoc.modules.users.domain.UserRepository
import com.regisoc.modules.users.domain.UserRole
import com.regisoc.shared.infrastructure.security.JwtService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Optional

class LoginUseCaseTest {

    private val authenticationManager = mockk<AuthenticationManager>()
    private val userRepository = mockk<UserRepository>()
    private val jwtService = mockk<JwtService>()
    private val passwordEncoder = BCryptPasswordEncoder()

    private lateinit var loginUseCase: LoginUseCase

    @BeforeEach
    fun setUp() {
        loginUseCase = LoginUseCase(authenticationManager, userRepository, jwtService)
    }

    @Test
    fun `should login successfully`() {
        val user = createUser(username = "admin", role = UserRole.ADMIN)
        val command = LoginCommand(username = "admin", password = "admin123")

        every { authenticationManager.authenticate(any()) } returns mockk()
        every { userRepository.findByUsername("admin") } returns Optional.of(user)
        every { jwtService.generateToken(user) } returns "jwt-token-123"

        val result = loginUseCase.execute(command)

        assertEquals("jwt-token-123", result.token)
        assertEquals(user.id, result.userId)
        assertEquals("admin", result.username)
        assertEquals("ADMIN", result.role)
        assertNull(result.clubId)
        verify { authenticationManager.authenticate(any()) }
        verify { jwtService.generateToken(user) }
    }

    @Test
    fun `should throw on invalid credentials`() {
        val command = LoginCommand(username = "admin", password = "wrong")

        every { authenticationManager.authenticate(any()) } throws BadCredentialsException("Bad credentials")

        assertThrows<BadCredentialsException> {
            loginUseCase.execute(command)
        }
    }

    @Test
    fun `should throw on deactivated user`() {
        val user = createUser(username = "inactive", role = UserRole.CLUB_MANAGER)
        user.deactivate()
        val command = LoginCommand(username = "inactive", password = "pass")

        every { authenticationManager.authenticate(any()) } returns mockk()
        every { userRepository.findByUsername("inactive") } returns Optional.of(user)

        assertThrows<IllegalStateException> {
            loginUseCase.execute(command)
        }
    }

    @Test
    fun `should include clubId in result for club manager`() {
        val club = Club(name = "Test Club", foundedYear = 2000, createdBy = "admin")
        val user = createUser(username = "manager", role = UserRole.CLUB_MANAGER, club = club)
        val command = LoginCommand(username = "manager", password = "pass")

        every { authenticationManager.authenticate(any()) } returns mockk()
        every { userRepository.findByUsername("manager") } returns Optional.of(user)
        every { jwtService.generateToken(user) } returns "jwt-token-456"

        val result = loginUseCase.execute(command)

        assertNotNull(result.clubId)
        assertEquals("CLUB_MANAGER", result.role)
    }

    private fun createUser(
        username: String,
        role: UserRole,
        club: Club? = null
    ): User {
        return User(
            name = "Test",
            surname = "User",
            documentNumber = "12345678",
            username = username,
            password = passwordEncoder.encode("hashedPassword"),
            role = role,
            club = club
        )
    }
}
