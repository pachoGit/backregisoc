package com.regisoc.modules.events.infrastructure.web

import jakarta.validation.constraints.NotNull

data class RegisterClubRequest(
    @field:NotNull val clubId: Long
)
