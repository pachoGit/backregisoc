package com.regisoc.modules.auth.application

import com.regisoc.modules.users.domain.UserRepository
import com.regisoc.shared.infrastructure.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class LoginUseCase(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    fun execute(command: LoginCommand): LoginResult {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(command.username, command.password)
        )

        val user = userRepository.findByUsername(command.username)
            .orElseThrow { IllegalArgumentException("Credenciales invalidas") }

        if (!user.isActive) {
            throw IllegalStateException("La cuenta del usuario esta desactivada")
        }

        val token = jwtService.generateToken(user)

        return LoginResult(
            token = token,
            userId = user.id,
            username = user.username,
            role = user.role.name,
            clubId = user.club?.id
        )
    }
}
