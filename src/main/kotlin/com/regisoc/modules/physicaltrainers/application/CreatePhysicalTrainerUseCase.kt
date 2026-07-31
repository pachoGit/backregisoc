package com.regisoc.modules.physicaltrainers.application

import com.regisoc.modules.clubs.domain.ClubRepository
import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainer
import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

data class CreatePhysicalTrainerCommand(
    val clubId: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
    val photoUrl: String?
)

@Service
class CreatePhysicalTrainerUseCase(
    private val repository: PhysicalTrainerRepository,
    private val clubRepository: ClubRepository
) {
    fun execute(command: CreatePhysicalTrainerCommand): PhysicalTrainer {
        val club = clubRepository.findById(command.clubId)
            .orElseThrow { EntityNotFoundException("Club not found: ${command.clubId}") }

        val physicalTrainer = PhysicalTrainer(
            club = club,
            firstName = command.firstName,
            lastName = command.lastName,
            documentNumber = command.documentNumber,
            age = command.age,
            dateOfBirth = command.dateOfBirth,
            photoUrl = command.photoUrl
        )
        return repository.save(physicalTrainer)
    }
}
