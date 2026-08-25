package com.regisoc.shared.infrastructure.security

import com.regisoc.modules.users.domain.User
import com.regisoc.modules.users.domain.UserRole
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JwtServiceTest {

    private lateinit var jwtService: JwtService

    private val secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
    private val expirationMs = 1800000L // 30 minutes

    @BeforeEach
    fun setUp() {
        jwtService = JwtService(secretKey, expirationMs)
    }

    @Test
    fun `should generate valid token`() {
        val user = createUser(username = "testuser", role = UserRole.ADMIN)

        val token = jwtService.generateToken(user)

        assertNotNull(token)
        assertTrue(token.isNotEmpty())
    }

    @Test
    fun `should extract username from token`() {
        val user = createUser(username = "testuser", role = UserRole.ADMIN)

        val token = jwtService.generateToken(user)
        val extractedUsername = jwtService.extractUsername(token)

        assertEquals("testuser", extractedUsername)
    }

    @Test
    fun `should extract role from token`() {
        val user = createUser(username = "admin", role = UserRole.ADMIN)

        val token = jwtService.generateToken(user)
        val extractedRole = jwtService.extractRole(token)

        assertEquals(UserRole.ADMIN, extractedRole)
    }

    @Test
    fun `should extract clubId from token`() {
        val user = createUser(username = "manager", role = UserRole.CLUB_MANAGER, clubId = 42L)

        val token = jwtService.generateToken(user)
        val extractedClubId = jwtService.extractClubId(token)

        assertEquals(42L, extractedClubId)
    }

    @Test
    fun `should return null clubId for admin user`() {
        val user = createUser(username = "admin", role = UserRole.ADMIN)

        val token = jwtService.generateToken(user)
        val extractedClubId = jwtService.extractClubId(token)

        assertNull(extractedClubId)
    }

    @Test
    fun `should validate token correctly`() {
        val user = createUser(username = "testuser", role = UserRole.ADMIN)
        val springUser = org.springframework.security.core.userdetails.User(
            user.username, user.password, true, true, true, true,
            listOf(org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMIN"))
        )

        val token = jwtService.generateToken(user)

        assertTrue(jwtService.isTokenValid(token, springUser))
    }

    @Test
    fun `should reject token with wrong username`() {
        val user = createUser(username = "testuser", role = UserRole.ADMIN)
        val otherUser = org.springframework.security.core.userdetails.User(
            "otheruser", "password", true, true, true, true,
            listOf(org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMIN"))
        )

        val token = jwtService.generateToken(user)

        assertFalse(jwtService.isTokenValid(token, otherUser))
    }

    @Test
    fun `should report remaining expiration time`() {
        val user = createUser(username = "testuser", role = UserRole.ADMIN)

        val token = jwtService.generateToken(user)
        val remainingMs = jwtService.getRemainingExpirationMs(token)

        assertTrue(remainingMs > 0)
        assertTrue(remainingMs <= expirationMs)
    }

    @Test
    fun `should not be expired immediately after creation`() {
        val user = createUser(username = "testuser", role = UserRole.ADMIN)

        val token = jwtService.generateToken(user)

        assertFalse(jwtService.isTokenExpired(token))
    }

    private fun createUser(
        username: String,
        role: UserRole,
        clubId: Long? = null
    ): User {
        return User(
            name = "Test",
            surname = "User",
            documentNumber = "12345678",
            username = username,
            password = "hashedPassword",
            role = role,
            clubId = clubId
        )
    }
}
