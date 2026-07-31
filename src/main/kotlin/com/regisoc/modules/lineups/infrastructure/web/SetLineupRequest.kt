package com.regisoc.modules.lineups.infrastructure.web

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class SetLineupRequest(
    @field:NotNull val matchId: Long,
    @field:NotNull val clubId: Long,
    @field:NotEmpty val playerIds: List<Long>
)
