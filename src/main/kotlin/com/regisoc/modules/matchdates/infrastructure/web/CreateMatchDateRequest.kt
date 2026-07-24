package com.regisoc.modules.matchdates.infrastructure.web

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CreateMatchDateRequest(
    @field:NotBlank val eventId: Long,
    @field:NotBlank val name: String,
    @field:NotNull val date: LocalDate
)
