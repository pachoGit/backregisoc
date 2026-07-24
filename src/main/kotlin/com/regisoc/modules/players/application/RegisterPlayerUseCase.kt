package com.regisoc.modules.players.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.players.domain.Player
import com.regisoc.modules.players.domain.PlayerPosition
import com.regisoc.modules.players.domain.PlayerRepository
import com.regisoc.shared.domain.FileStorageService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

data class RegisterPlayerCommand(
    val clubId: String,
    val name: String,
    val dateOfBirth: LocalDate?,
    val position: PlayerPosition?,
    val photo: MultipartFile?,
    val documentFront: MultipartFile?,
    val documentBack: MultipartFile?
)

@Service
class RegisterPlayerUseCase(
    private val repository: PlayerRepository,
    private val clubRepository: ClubRepository,
    private val fileStorage: FileStorageService
) {
    fun execute(command: RegisterPlayerCommand): Player {
        val club = clubRepository.findById(command.clubId)
            .orElseThrow { EntityNotFoundException("Club not found: ${command.clubId}") }

        val photoUrl = command.photo?.let { fileStorage.store(it, "players/photos") }
        val docFrontUrl = command.documentFront?.let { fileStorage.store(it, "players/documents") }
        val docBackUrl = command.documentBack?.let { fileStorage.store(it, "players/documents") }

        val player = Player(
            club = club,
            name = command.name,
            dateOfBirth = command.dateOfBirth,
            position = command.position,
            photoUrl = photoUrl,
            documentFrontUrl = docFrontUrl,
            documentBackUrl = docBackUrl
        )
        return repository.save(player)
    }
}
