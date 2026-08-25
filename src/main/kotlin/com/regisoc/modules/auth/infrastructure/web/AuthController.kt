package com.regisoc.modules.auth.infrastructure.web

import com.regisoc.modules.auth.application.GetMeUseCase
import com.regisoc.modules.auth.application.LoginCommand
import com.regisoc.modules.auth.application.LoginUseCase
import com.regisoc.modules.auth.application.LogoutCommand
import com.regisoc.modules.auth.application.LogoutUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getMeUseCase: GetMeUseCase
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val result = loginUseCase.execute(
            LoginCommand(
                username = request.username,
                password = request.password
            )
        )
        return ResponseEntity.ok(
            LoginResponse(
                token = result.token,
                userId = result.userId,
                username = result.username,
                role = result.role,
                clubId = result.clubId
            )
        )
    }

    @PostMapping("/logout")
    fun logout(@RequestBody(required = false) request: LogoutRequest?): ResponseEntity<LogoutResponse> {
        val token = request?.token
            ?: throw IllegalArgumentException("Token is required for logout")

        logoutUseCase.execute(LogoutCommand(token = token))

        return ResponseEntity.ok(LogoutResponse(message = "Logged out successfully"))
    }

    @GetMapping("/me")
    fun me(): ResponseEntity<MeResponse> {
        val result = getMeUseCase.execute()
        return ResponseEntity.ok(
            MeResponse(
                id = result.id,
                name = result.name,
                surname = result.surname,
                documentNumber = result.documentNumber,
                username = result.username,
                role = result.role,
                club = result.club?.let { ClubInfoResponse(id = it.id, name = it.name) }
            )
        )
    }
}
