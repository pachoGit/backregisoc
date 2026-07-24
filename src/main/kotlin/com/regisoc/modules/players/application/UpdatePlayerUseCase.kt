package com.regisoc.modules.players.application

import com.regisoc.modules.players.domain.PlayerPosition
import com.regisoc.modules.players.domain.PlayerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

data class UpdatePlayerCommand(
    val id: Long,
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
class UpdatePlayerUseCase(
    private val repository: PlayerRepository
) {
    fun execute(command: UpdatePlayerCommand) {
        val player = repository.findById(command.id)
            .orElseThrow { EntityNotFoundException("Player not found with id: ${command.id}") }

        player.update(
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
        repository.save(player)
    }
}
