package com.regisoc.modules.matches.infrastructure.web

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CreateMatchRequest(
    @field:NotNull val matchDateId: Long,
    @field:NotNull val homeClubId: Long,
    @field:NotNull val awayClubId: Long,
    @field:NotNull val scheduledTime: LocalDateTime
)
