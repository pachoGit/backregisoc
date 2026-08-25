package com.regisoc.modules.users.infrastructure.web

data class AuthResponse(
    val token: String,
    val userId: Long,
    val username: String,
    val role: String,
    val clubId: Long?
)
