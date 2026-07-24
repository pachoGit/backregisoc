package com.regisoc.modules.events.infrastructure.web

import jakarta.validation.constraints.NotBlank

data class RegisterClubRequest(
    @field:NotBlank val clubId: String
)
