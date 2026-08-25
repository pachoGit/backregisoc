package com.regisoc.modules.auth.application

import com.regisoc.modules.users.domain.UserRepository
import com.regisoc.shared.infrastructure.security.CurrentUserHelper
import org.springframework.stereotype.Service

data class MeResult(
    val id: Long,
    val name: String,
    val surname: String,
    val documentNumber: String,
    val username: String,
    val role: String,
    val club: ClubInfo?
)

data class ClubInfo(
    val id: Long,
    val name: String
)

@Service
class GetMeUseCase(
    private val currentUserHelper: CurrentUserHelper,
    private val userRepository: UserRepository
) {
    fun execute(): MeResult {
        val userId = currentUserHelper.getCurrentUserId()
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalStateException("User not found") }

        val club = user.club?.let { ClubInfo(id = it.id, name = it.name) }

        return MeResult(
            id = user.id,
            name = user.name,
            surname = user.surname,
            documentNumber = user.documentNumber,
            username = user.username,
            role = user.role.name,
            club = club
        )
    }
}
