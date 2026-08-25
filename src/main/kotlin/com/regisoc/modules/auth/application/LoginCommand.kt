package com.regisoc.modules.auth.application

data class LoginCommand(
    val username: String,
    val password: String
)

data class LoginResult(
    val token: String,
    val userId: Long,
    val username: String,
    val role: String,
    val clubId: Long?
)
