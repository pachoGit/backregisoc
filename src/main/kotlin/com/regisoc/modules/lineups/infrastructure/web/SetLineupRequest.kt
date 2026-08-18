package com.regisoc.modules.lineups.infrastructure.web

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class SetLineupRequest(
    @field:NotNull val matchId: Long,
    @field:NotNull val clubId: Long,
    @field:Size(max = 22) val playerIds: List<Long> = emptyList(),
    val coachId: Long? = null,
    val physicalTrainerId: Long? = null
)
