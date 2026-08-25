package com.regisoc.modules.auth.application

import com.regisoc.shared.infrastructure.security.JwtService
import org.springframework.stereotype.Service

data class LogoutCommand(
    val token: String
)

@Service
class LogoutUseCase(
    private val jwtService: JwtService
) {
    fun execute(command: LogoutCommand) {
        // El token expira naturalmente según su TTL (30 min).
        // No se requiere invalidación inmediata.
    }
}
