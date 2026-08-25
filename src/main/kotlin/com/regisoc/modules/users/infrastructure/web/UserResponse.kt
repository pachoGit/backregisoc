package com.regisoc.modules.users.infrastructure.web

import com.regisoc.modules.users.domain.User
import com.regisoc.modules.users.domain.UserRole
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val name: String,
    val surname: String,
    val documentNumber: String,
    val username: String,
    val role: UserRole,
    val clubId: Long?,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(user: User) = UserResponse(
            id = user.id,
            name = user.name,
            surname = user.surname,
            documentNumber = user.documentNumber,
            username = user.username,
            role = user.role,
            clubId = user.clubId,
            isActive = user.isActive,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }
}
