package com.regisoc.shared.infrastructure.security

import com.regisoc.modules.users.domain.User
import com.regisoc.modules.users.domain.UserRole
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class PasswordHashingTest {

    private lateinit var passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder

    @BeforeEach
    fun setUp() {
        passwordEncoder = org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
    }

    @Test
    fun `should hash password correctly`() {
        val rawPassword = "mySecurePassword123"
        val hashedPassword = passwordEncoder.encode(rawPassword)

        assertNotEquals(rawPassword, hashedPassword)
        assertTrue(passwordEncoder.matches(rawPassword, hashedPassword))
        assertTrue(hashedPassword.startsWith("\$2a\$"))
    }

    @Test
    fun `should verify password correctly`() {
        val rawPassword = "testPassword"
        val hashedPassword = passwordEncoder.encode(rawPassword)

        assertTrue(passwordEncoder.matches(rawPassword, hashedPassword))
        assertFalse(passwordEncoder.matches("wrongPassword", hashedPassword))
    }

    @Test
    fun `should generate different hashes for same password`() {
        val rawPassword = "samePassword"
        val hash1 = passwordEncoder.encode(rawPassword)
        val hash2 = passwordEncoder.encode(rawPassword)

        assertNotEquals(hash1, hash2)
        assertTrue(passwordEncoder.matches(rawPassword, hash1))
        assertTrue(passwordEncoder.matches(rawPassword, hash2))
    }
}
