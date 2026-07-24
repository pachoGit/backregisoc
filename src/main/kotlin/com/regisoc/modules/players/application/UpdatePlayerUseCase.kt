package com.regisoc.modules.players.application

import com.regisoc.modules.players.domain.PlayerPosition
import com.regisoc.modules.players.domain.PlayerRepository
import com.regisoc.shared.domain.FileStorageService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

data class UpdatePlayerCommand(
    val id: String,
    val name: String,
    val dateOfBirth: LocalDate?,
    val position: PlayerPosition?,
    val photo: MultipartFile?,
    val documentFront: MultipartFile?,
    val documentBack: MultipartFile?
)

@Service
class UpdatePlayerUseCase(
    private val repository: PlayerRepository,
    private val fileStorage: FileStorageService
) {
    fun execute(command: UpdatePlayerCommand) {
        val player = repository.findById(command.id)
            .orElseThrow { EntityNotFoundException("Player not found with id: ${command.id}") }

        val photoUrl = command.photo?.let { fileStorage.store(it, "players/photos") } ?: player.photoUrl
        val docFrontUrl = command.documentFront?.let { fileStorage.store(it, "players/documents") } ?: player.documentFrontUrl
        val docBackUrl = command.documentBack?.let { fileStorage.store(it, "players/documents") } ?: player.documentBackUrl

        player.update(command.name, command.dateOfBirth, command.position, photoUrl, docFrontUrl, docBackUrl)
        repository.save(player)
    }
}
