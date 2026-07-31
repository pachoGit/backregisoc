package com.regisoc.modules.physicaltrainers.application

import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

data class UpdatePhysicalTrainerCommand(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
    val photoUrl: String?
)

@Service
class UpdatePhysicalTrainerUseCase(
    private val repository: PhysicalTrainerRepository
) {
    fun execute(command: UpdatePhysicalTrainerCommand) {
        val physicalTrainer = repository.findById(command.id)
            .orElseThrow { EntityNotFoundException("PhysicalTrainer not found with id: ${command.id}") }

        physicalTrainer.update(
            firstName = command.firstName,
            lastName = command.lastName,
            documentNumber = command.documentNumber,
            age = command.age,
            dateOfBirth = command.dateOfBirth,
            photoUrl = command.photoUrl
        )
        repository.save(physicalTrainer)
    }
}
