package com.regisoc.modules.users.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserTest {

    @Test
    fun `should create user with required fields`() {
        val user = User(
            name = "John",
            surname = "Doe",
            documentNumber = "12345678",
            username = "johndoe",
            password = "hashedPassword",
            role = UserRole.ADMIN
        )

        assertEquals("John", user.name)
        assertEquals("Doe", user.surname)
        assertEquals("12345678", user.documentNumber)
        assertEquals("johndoe", user.username)
        assertEquals(UserRole.ADMIN, user.role)
        assertNull(user.clubId)
        assertTrue(user.isActive)
    }

    @Test
    fun `should create club manager with clubId`() {
        val user = User(
            name = "Jane",
            surname = "Smith",
            documentNumber = "87654321",
            username = "janesmith",
            password = "hashedPassword",
            role = UserRole.CLUB_MANAGER,
            clubId = 1L
        )

        assertEquals(UserRole.CLUB_MANAGER, user.role)
        assertEquals(1L, user.clubId)
    }

    @Test
    fun `should reject admin with clubId`() {
        assertThrows<IllegalArgumentException> {
            User(
                name = "John",
                surname = "Doe",
                documentNumber = "12345678",
                username = "johndoe",
                password = "hashedPassword",
                role = UserRole.ADMIN,
                clubId = 1L
            )
        }
    }

    @Test
    fun `should update user fields`() {
        val user = User(
            name = "John",
            surname = "Doe",
            documentNumber = "12345678",
            username = "johndoe",
            password = "hashedPassword",
            role = UserRole.ADMIN
        )

        user.update("Jane", "Smith", "87654321")

        assertEquals("Jane", user.name)
        assertEquals("Smith", user.surname)
        assertEquals("87654321", user.documentNumber)
    }

    @Test
    fun `should deactivate user`() {
        val user = User(
            name = "John",
            surname = "Doe",
            documentNumber = "12345678",
            username = "johndoe",
            password = "hashedPassword",
            role = UserRole.ADMIN
        )

        user.deactivate()

        assertFalse(user.isActive)
        assertNotNull(user.deletedAt)
    }
}
