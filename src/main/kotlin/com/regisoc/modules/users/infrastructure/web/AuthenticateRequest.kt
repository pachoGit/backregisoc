package com.regisoc.modules.users.infrastructure.web

import jakarta.validation.constraints.NotBlank

data class AuthenticateRequest(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String
)
