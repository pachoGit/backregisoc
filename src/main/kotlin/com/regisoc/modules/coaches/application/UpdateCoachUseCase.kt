package com.regisoc.modules.coaches.application

import com.regisoc.modules.coaches.domain.CoachRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

data class UpdateCoachCommand(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
    val photoUrl: String?,
    val documentFrontUrl: String?,
    val documentBackUrl: String?
)

@Service
class UpdateCoachUseCase(
    private val repository: CoachRepository
) {
    fun execute(command: UpdateCoachCommand) {
        val coach = repository.findById(command.id)
            .orElseThrow { EntityNotFoundException("Coach not found with id: ${command.id}") }

        coach.update(
            firstName = command.firstName,
            lastName = command.lastName,
            documentNumber = command.documentNumber,
            age = command.age,
            dateOfBirth = command.dateOfBirth,
            photoUrl = command.photoUrl,
            documentFrontUrl = command.documentFrontUrl,
            documentBackUrl = command.documentBackUrl
        )
        repository.save(coach)
    }
}
