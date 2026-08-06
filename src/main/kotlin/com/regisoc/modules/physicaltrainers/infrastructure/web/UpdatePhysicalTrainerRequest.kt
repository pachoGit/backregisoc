package com.regisoc.modules.physicaltrainers.infrastructure.web

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class UpdatePhysicalTrainerRequest(
    @field:NotBlank val firstName: String,
    @field:NotBlank val lastName: String,
    @field:NotBlank val documentNumber: String,
    val age: Int,
    @field:NotNull val dateOfBirth: LocalDate,
    val photoUrl: String? = null,
    val documentFrontUrl: String? = null,
    val documentBackUrl: String? = null
)
