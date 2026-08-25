package com.regisoc.modules.auth.infrastructure.web

data class MeResponse(
    val id: Long,
    val name: String,
    val surname: String,
    val documentNumber: String,
    val username: String,
    val role: String,
    val club: ClubInfoResponse?
)
