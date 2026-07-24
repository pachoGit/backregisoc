package com.regisoc.modules.events.infrastructure.web

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CreateEventRequest(
    @field:NotBlank val name: String,
    val description: String?,
    @field:NotNull val startDate: LocalDate,
    @field:NotNull @field:Future val endDate: LocalDate
)
