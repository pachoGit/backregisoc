package com.regisoc.modules.matches.infrastructure.web

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CreateMatchRequest(
    @field:NotBlank val matchDateId: Long,
    @field:NotBlank val homeClubId: Long,
    @field:NotBlank val awayClubId: Long,
    @field:NotNull val scheduledTime: LocalDateTime
)
