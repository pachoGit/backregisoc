package com.regisoc.modules.matches.infrastructure.web

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CreateMatchRequest(
    @field:NotBlank val matchDateId: String,
    @field:NotBlank val homeClubId: String,
    @field:NotBlank val awayClubId: String,
    @field:NotNull val scheduledTime: LocalDateTime
)
