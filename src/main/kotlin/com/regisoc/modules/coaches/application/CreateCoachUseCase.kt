package com.regisoc.modules.coaches.application

import com.regisoc.modules.coaches.domain.Coach
import com.regisoc.modules.coaches.domain.CoachRepository
import com.regisoc.modules.clubs.domain.ClubRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

data class CreateCoachCommand(
    val clubId: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
    val photoUrl: String?
)

@Service
class CreateCoachUseCase(
    private val repository: CoachRepository,
    private val clubRepository: ClubRepository
) {
    fun execute(command: CreateCoachCommand): Coach {
        val club = clubRepository.findById(command.clubId)
            .orElseThrow { EntityNotFoundException("Club not found: ${command.clubId}") }

        val coach = Coach(
            club = club,
            firstName = command.firstName,
            lastName = command.lastName,
            documentNumber = command.documentNumber,
            age = command.age,
            dateOfBirth = command.dateOfBirth,
            photoUrl = command.photoUrl
        )
        return repository.save(coach)
    }
}
