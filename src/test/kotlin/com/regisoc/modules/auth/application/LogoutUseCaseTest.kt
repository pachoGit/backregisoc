package com.regisoc.modules.auth.application

import com.regisoc.shared.infrastructure.security.JwtService
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogoutUseCaseTest {

    private val jwtService = mockk<JwtService>()
    private lateinit var logoutUseCase: LogoutUseCase

    @BeforeEach
    fun setUp() {
        logoutUseCase = LogoutUseCase(jwtService)
    }

    @Test
    fun `should complete logout without error`() {
        val command = LogoutCommand(token = "any-token")

        logoutUseCase.execute(command)
    }
}
