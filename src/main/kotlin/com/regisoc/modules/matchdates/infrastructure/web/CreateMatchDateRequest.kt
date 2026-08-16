package com.regisoc.modules.matchdates.infrastructure.web

import com.regisoc.modules.matchdates.domain.MatchDateStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CreateMatchDateRequest(
    @field:NotNull val eventId: Long,
    @field:NotBlank val name: String,
    @field:NotNull val date: LocalDate,
    val status: MatchDateStatus = MatchDateStatus.UPCOMING
)
