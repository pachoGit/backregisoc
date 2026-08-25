package com.regisoc.modules.users.application

import com.regisoc.modules.users.domain.UserRepository
import com.regisoc.shared.infrastructure.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

data class AuthenticateCommand(
    val username: String,
    val password: String
)

data class AuthenticateResult(
    val token: String,
    val userId: Long,
    val username: String,
    val role: String,
    val clubId: Long?
)

@Service
class AuthenticateUseCase(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {
    fun execute(command: AuthenticateCommand): AuthenticateResult {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(command.username, command.password)
        )

        val user = userRepository.findByUsername(command.username)
            .orElseThrow { IllegalArgumentException("Invalid credentials") }

        if (!user.isActive) {
            throw IllegalStateException("User account is deactivated")
        }

        val token = jwtService.generateToken(user)

        return AuthenticateResult(
            token = token,
            userId = user.id,
            username = user.username,
            role = user.role.name,
            clubId = user.clubId
        )
    }
}
