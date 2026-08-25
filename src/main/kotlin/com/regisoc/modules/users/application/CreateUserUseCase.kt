package com.regisoc.modules.users.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.users.domain.User
import com.regisoc.modules.users.domain.UserRepository
import com.regisoc.modules.users.domain.UserRole
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

data class CreateUserCommand(
    val name: String,
    val surname: String,
    val documentNumber: String,
    val username: String,
    val password: String,
    val role: UserRole,
    val clubId: Long? = null
)

@Service
class CreateUserUseCase(
    private val userRepository: UserRepository,
    private val clubRepository: ClubRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun execute(command: CreateUserCommand): User {
        if (userRepository.existsByUsername(command.username)) {
            throw IllegalStateException("Username already exists: ${command.username}")
        }

        if (command.clubId != null && command.role != UserRole.CLUB_MANAGER) {
            throw IllegalArgumentException("Only CLUB_MANAGER users can be associated with a club")
        }

        val club = command.clubId?.let { clubId ->
            clubRepository.findById(clubId)
                .orElseThrow { jakarta.persistence.EntityNotFoundException("Club not found with id: $clubId") }
        }

        if (command.clubId != null && club == null) {
            throw jakarta.persistence.EntityNotFoundException("Club not found with id: ${command.clubId}")
        }

        val user = User(
            name = command.name,
            surname = command.surname,
            documentNumber = command.documentNumber,
            username = command.username,
            password = passwordEncoder.encode(command.password),
            role = command.role,
            club = club
        )

        return userRepository.save(user)
    }
}
