package com.regisoc.shared.infrastructure.security

import com.regisoc.modules.users.domain.UserRepository
import com.regisoc.modules.users.domain.UserRole
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class CurrentUserHelper(
    private val userRepository: UserRepository
) {

    fun getCurrentUserId(): Long {
        val username = getCurrentUsername()
        val user = userRepository.findByUsername(username)
            .orElseThrow { IllegalStateException("Authenticated user not found: $username") }
        return user.id
    }

    fun getCurrentUserRole(): UserRole {
        val username = getCurrentUsername()
        val user = userRepository.findByUsername(username)
            .orElseThrow { IllegalStateException("Authenticated user not found: $username") }
        return user.role
    }

    fun getCurrentUserClubId(): Long? {
        val username = getCurrentUsername()
        val user = userRepository.findByUsername(username)
            .orElseThrow { IllegalStateException("Authenticated user not found: $username") }
        return user.clubId
    }

    fun isAdmin(): Boolean = getCurrentUserRole() == UserRole.ADMIN

    private fun getCurrentUsername(): String {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("No authentication found in security context")
        return authentication.name
    }
}
