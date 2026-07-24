package com.regisoc.modules.players.infrastructure.web

import com.regisoc.modules.players.domain.PlayerPosition
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class CreatePlayerRequest(
    @field:NotBlank val clubId: String,
    @field:NotBlank val name: String,
    val dateOfBirth: LocalDate?,
    val position: PlayerPosition?
)
