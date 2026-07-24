package com.regisoc.modules.players.infrastructure.web

import com.regisoc.modules.players.domain.PlayerPosition
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class UpdatePlayerRequest(
    @field:NotBlank val firstName: String,
    @field:NotBlank val lastName: String,
    @field:NotBlank val documentNumber: String,
    val age: Int,
    @field:NotNull val dateOfBirth: LocalDate,
    val position: PlayerPosition?,
    val photoUrl: String? = null,
    val documentFrontUrl: String? = null,
    val documentBackUrl: String? = null
)
