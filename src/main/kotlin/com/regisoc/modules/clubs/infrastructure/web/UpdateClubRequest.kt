package com.regisoc.modules.clubs.infrastructure.web

import jakarta.validation.constraints.NotBlank

data class UpdateClubRequest(
    @field:NotBlank val name: String,
    val foundedYear: Int?,
    val crestUrl: String?,
    val description: String?
)
