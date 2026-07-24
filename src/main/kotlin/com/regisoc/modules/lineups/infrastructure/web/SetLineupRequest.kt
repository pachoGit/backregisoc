package com.regisoc.modules.lineups.infrastructure.web

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class SetLineupRequest(
    @field:NotBlank val matchId: String,
    @field:NotBlank val clubId: String,
    @field:NotEmpty val playerIds: List<String>
)
