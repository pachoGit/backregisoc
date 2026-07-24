package com.regisoc.modules.clubs.application

import com.regisoc.modules.clubs.domain.ClubRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

data class UpdateClubCommand(
    val id: String,
    val name: String,
    val foundedYear: Int?,
    val crestUrl: String?,
    val description: String?
)

@Service
class UpdateClubUseCase(private val repository: ClubRepository) {
    fun execute(command: UpdateClubCommand) {
        val club = repository.findById(command.id)
            .orElseThrow { EntityNotFoundException("Club not found with id: ${command.id}") }
        club.update(command.name, command.foundedYear, command.crestUrl, command.description)
        repository.save(club)
    }
}
