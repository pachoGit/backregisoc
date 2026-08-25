package com.regisoc.modules.users.infrastructure.web

import com.regisoc.modules.users.domain.UserRole
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateUserRequest(
    @field:NotBlank val name: String,
    @field:NotBlank val surname: String,
    @field:NotBlank val documentNumber: String,
    @field:NotBlank val username: String,
    @field:NotBlank val password: String,
    @field:NotNull val role: UserRole,
    val clubId: Long? = null
)
