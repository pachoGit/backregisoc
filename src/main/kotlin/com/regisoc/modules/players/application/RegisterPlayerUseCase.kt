package com.regisoc.modules.players.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.players.domain.Player
import com.regisoc.modules.players.domain.PlayerPosition
import com.regisoc.modules.players.domain.PlayerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

data class RegisterPlayerCommand(
    val clubId: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
    val position: PlayerPosition?,
    val photoUrl: String?,
    val documentFrontUrl: String?,
    val documentBackUrl: String?
)

@Service
class RegisterPlayerUseCase(
    private val repository: PlayerRepository,
    private val clubRepository: ClubRepository
) {
    fun execute(command: RegisterPlayerCommand): Player {
        println("Entro al caso de uso")
        val club = clubRepository.findById(command.clubId)
            .orElseThrow { EntityNotFoundException("Club not found: ${command.clubId}") }

        val player = Player(
            club = club,
            firstName = command.firstName,
            lastName = command.lastName,
            documentNumber = command.documentNumber,
            age = command.age,
            dateOfBirth = command.dateOfBirth,
            position = command.position,
            photoUrl = command.photoUrl,
            documentFrontUrl = command.documentFrontUrl,
            documentBackUrl = command.documentBackUrl
        )
        return repository.save(player)
    }
}
