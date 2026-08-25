package com.regisoc.modules.auth.infrastructure.web

data class LoginResponse(
    val token: String,
    val userId: Long,
    val username: String,
    val role: String,
    val clubId: Long?
)
