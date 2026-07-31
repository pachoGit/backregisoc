package com.regisoc.modules.coaches.application

import com.regisoc.modules.coaches.domain.CoachRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class DeleteCoachUseCase(private val repository: CoachRepository) {
    fun execute(id: Long) {
        val coach = repository.findById(id)
            .orElseThrow { EntityNotFoundException("Coach not found with id: $id") }
        repository.delete(coach)
    }
}
