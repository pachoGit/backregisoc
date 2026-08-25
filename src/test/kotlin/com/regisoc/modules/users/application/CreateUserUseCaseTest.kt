package com.regisoc.modules.users.application

import com.regisoc.modules.users.domain.User
import com.regisoc.modules.users.domain.UserRepository
import com.regisoc.modules.users.domain.UserRole
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class CreateUserUseCaseTest {

    private val repository = mockk<UserRepository>()
    private val passwordEncoder = BCryptPasswordEncoder()
    private val useCase = CreateUserUseCase(repository, passwordEncoder)

    @Test
    fun `should create user successfully`() {
        val command = CreateUserCommand(
            name = "John",
            surname = "Doe",
            documentNumber = "12345678",
            username = "johndoe",
            password = "password123",
            role = UserRole.ADMIN
        )

        every { repository.existsByUsername("johndoe") } returns false
        every { repository.save(any()) } answers { firstArg() }

        val result = useCase.execute(command)

        assertEquals("John", result.name)
        assertEquals("Doe", result.surname)
        assertEquals("johndoe", result.username)
        assertEquals(UserRole.ADMIN, result.role)
        assertNotEquals("password123", result.password)
        assertTrue(passwordEncoder.matches("password123", result.password))
        verify { repository.save(any()) }
    }

    @Test
    fun `should throw exception when username already exists`() {
        val command = CreateUserCommand(
            name = "John",
            surname = "Doe",
            documentNumber = "12345678",
            username = "johndoe",
            password = "password123",
            role = UserRole.ADMIN
        )

        every { repository.existsByUsername("johndoe") } returns true

        assertThrows<IllegalStateException> {
            useCase.execute(command)
        }
    }

    @Test
    fun `should create club manager with clubId`() {
        val command = CreateUserCommand(
            name = "Jane",
            surname = "Smith",
            documentNumber = "87654321",
            username = "janesmith",
            password = "password123",
            role = UserRole.CLUB_MANAGER,
            clubId = 1L
        )

        every { repository.existsByUsername("janesmith") } returns false
        every { repository.save(any()) } answers { firstArg() }

        val result = useCase.execute(command)

        assertEquals(UserRole.CLUB_MANAGER, result.role)
        assertEquals(1L, result.clubId)
    }

    @Test
    fun `should reject admin with clubId`() {
        val command = CreateUserCommand(
            name = "John",
            surname = "Doe",
            documentNumber = "12345678",
            username = "johndoe",
            password = "password123",
            role = UserRole.ADMIN,
            clubId = 1L
        )

        every { repository.existsByUsername("johndoe") } returns false

        assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }
    }
}
