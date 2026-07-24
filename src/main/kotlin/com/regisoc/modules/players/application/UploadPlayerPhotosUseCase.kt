package com.regisoc.modules.players.application

import com.regisoc.modules.players.domain.PlayerRepository
import com.regisoc.shared.domain.FileStorageService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

data class UploadPlayerPhotosCommand(
    val playerId: Long,
    val photo: MultipartFile?,
    val documentFront: MultipartFile?,
    val documentBack: MultipartFile?
)

@Service
class UploadPlayerPhotosUseCase(
    private val repository: PlayerRepository,
    private val fileStorage: FileStorageService
) {
    fun execute(command: UploadPlayerPhotosCommand) {
        val player = repository.findById(command.playerId)
            .orElseThrow { EntityNotFoundException("Player not found with id: ${command.playerId}") }

        val photoUrl = command.photo?.let { fileStorage.store(it, "players/photos") }
        val docFrontUrl = command.documentFront?.let { fileStorage.store(it, "players/documents") }
        val docBackUrl = command.documentBack?.let { fileStorage.store(it, "players/documents") }

        player.updatePhotos(
            photoUrl = photoUrl ?: player.photoUrl,
            documentFrontUrl = docFrontUrl ?: player.documentFrontUrl,
            documentBackUrl = docBackUrl ?: player.documentBackUrl
        )
        repository.save(player)
    }
}