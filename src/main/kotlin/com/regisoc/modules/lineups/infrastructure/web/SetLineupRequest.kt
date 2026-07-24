package com.regisoc.modules.lineups.infrastructure.web

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class SetLineupRequest(
    @field:NotBlank val matchId: Long,
    @field:NotBlank val clubId: Long,
    @field:NotEmpty val playerIds: List<Long>
)
