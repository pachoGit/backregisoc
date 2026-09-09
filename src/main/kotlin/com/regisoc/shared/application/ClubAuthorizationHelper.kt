package com.regisoc.shared.application

import com.regisoc.shared.infrastructure.security.CurrentUserHelper
import org.springframework.stereotype.Component
import org.springframework.security.access.AccessDeniedException

@Component
class ClubAuthorizationHelper(
    private val currentUserHelper: CurrentUserHelper
) {
    fun validateClubAccess(targetClubId: Long) {
        val user = currentUserHelper.getUser()
        if (!user.isAdmin() && user.club?.id != targetClubId) {
            throw AccessDeniedException("Sólo puedes acceder a los recursos de tu propio club.")
        }
    }

    fun requireClubId(): Long {
        val user = currentUserHelper.getUser()
        if (user.isAdmin()) {
            throw IllegalStateException("Admin users must specify a clubId explicitly")
        }
        return user.club?.id ?: throw AccessDeniedException("User is not associated with any club")
    }

    fun resolveEffectiveClubId(requestedClubId: Long? = null): Long {
        val user = currentUserHelper.getUser()
        return if (user.isAdmin()) {
            requestedClubId ?: throw IllegalArgumentException("Club ID is required for admin users")
        } else {
            user.club?.id ?: throw AccessDeniedException("User is not associated with any club")
        }
    }

    fun isClubManagerOf(targetClubId: Long): Boolean {
        val user = currentUserHelper.getUser()
        return !user.isAdmin() && user.club?.id == targetClubId
    }
}
